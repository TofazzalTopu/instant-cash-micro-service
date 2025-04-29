package com.info.api.service.impl.ic;

import com.info.dto.constants.Constants;
import com.info.api.dto.RoutingNumberDTO;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.ic.ICOutstandingRemittanceDTO;
import com.info.api.dto.ic.ICOutstandingTransactionDTO;
import com.info.api.entity.ApiTrace;
import com.info.api.entity.Branch;
import com.info.api.entity.MbkBrn;
import com.info.api.entity.RemittanceData;
import com.info.api.mapper.ICOutstandingRemittanceMapper;
import com.info.api.service.common.ApiTraceService;
import com.info.api.service.common.RemittanceDataService;
import com.info.api.service.common.RemittanceProcessService;
import com.info.api.service.feignclient.branch.BranchServiceFeignClient;
import com.info.api.service.ic.ICOutstandingRemittanceService;
import com.info.api.util.ApiUtil;
import com.info.api.util.RemittanceValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.info.api.util.ObjectConverter.convertObjectToString;

@Service
@Transactional
@RequiredArgsConstructor
public class ICOutstandingRemittanceServiceImpl implements ICOutstandingRemittanceService {
    public static final Logger logger = LoggerFactory.getLogger(ICOutstandingRemittanceServiceImpl.class);

    private final RestTemplate restTemplate;
    private final ApiTraceService apiTraceService;
    private final RemittanceDataService remittanceDataService;
    private final ICOutstandingRemittanceMapper icRemittanceMapper;
    private final BranchServiceFeignClient branchServiceFeignClient;
    private final RemittanceProcessService remittanceProcessService;

    @Override
    public List<RemittanceData> fetchICOutstandingRemittance(ICExchangePropertyDTO icDTO) {
        List<RemittanceData> remittanceDataArrayList = new ArrayList<>();
        List<ICOutstandingTransactionDTO> transactionDTOArrayList = new ArrayList<>();

        if (ApiUtil.isInvalidICProperties(icDTO, icDTO.getOutstandingUrl())) {
            logger.error(Constants.EXCHANGE_HOUSE_PROPERTY_NOT_EXIST_FOR_OUTSTANDING_REMITTANCE);
            return remittanceDataArrayList;
        }

        String response = "";
        String status = Constants.API_STATUS_VALID;
        ApiTrace trace = apiTraceService.create(icDTO.getExchangeCode(), Constants.REQUEST_TYPE_DOWNLOAD_REQ, null);
        if (Objects.isNull(trace)) return new ArrayList<>();
        try {
            ResponseEntity<ICOutstandingRemittanceDTO> responseEntity = sendICOutstandingRemittanceDownloadRequest(icDTO, trace.getId());
            response = convertObjectToString(responseEntity.getBody());
            logger.info("\nICOutstandingRemittance API status: {}\n Response: {}\n", responseEntity.getStatusCode(), response);

            Optional.ofNullable(responseEntity.getBody()).map(ICOutstandingRemittanceDTO::getData).ifPresent(transactionDTOArrayList::addAll);

            if (transactionDTOArrayList.isEmpty()) return removeStrace(remittanceDataArrayList, trace.getId());
            remittanceDataArrayList = prepareAndProcessOutstandingRemittance(transactionDTOArrayList, icDTO.getExchangeCode(), trace);
        } catch (Exception e) {
            response = e.getMessage();
            status = Constants.API_STATUS_ERROR;
            logger.error(" fetchICOutstandingRemittance-> TraceID: {}, Error: {}", trace.getId(), e.getMessage());
        }

        apiTraceService.saveApiTrace(trace, "", response, status);

        return remittanceDataArrayList;
    }

    private List<RemittanceData> prepareAndProcessOutstandingRemittance(List<ICOutstandingTransactionDTO> transactionDTOArrayList, String exchangeCode, ApiTrace trace) {
        logger.info("\ntransactionDTOArrayList count: {} ", transactionDTOArrayList.size());
        List<String> branchRoutings = RemittanceValidator.getBranchRoutingNumbers(transactionDTOArrayList);
        List<Integer> routingNumbers = branchRoutings.stream().map(Integer::valueOf).collect(Collectors.toList());

        RoutingNumberDTO<String> routingNumberDTO = new RoutingNumberDTO<>(branchRoutings);
        RoutingNumberDTO<Integer> routingNumber = new RoutingNumberDTO<>(routingNumbers);
        Map<Integer, Branch> branchMap = branchServiceFeignClient.findAllByRoutingNumber(routingNumberDTO).stream().distinct().collect(Collectors.toMap(Branch::getRoutingNumber, Function.identity()));
        Map<String, MbkBrn> mbkBrnMap = branchServiceFeignClient.findAllMbkbrnByBranchRouting(routingNumber).stream().distinct().collect(Collectors.toMap(m -> m.getMbkbrnKey().getBranchRouting(), Function.identity(), (existing, replacement) -> existing));

        List<String> references = transactionDTOArrayList.stream().map(ICOutstandingTransactionDTO::getReference).distinct().collect(Collectors.toList());
        List<String> existingReferences = getExistingReferences(exchangeCode, references);

        List<RemittanceData> remittanceList = transactionDTOArrayList.stream().map(e -> mapDuplicate(e, existingReferences)).map(dto -> icRemittanceMapper.prepareRemittanceData(dto, exchangeCode, trace, branchMap, mbkBrnMap)).collect(Collectors.toList());

        List<RemittanceData> validRemittanceList = filterValidRemittances(remittanceList);

        remittanceProcessService.processAndSaveRemittanceData(validRemittanceList, exchangeCode, Constants.EXCHANGE_HOUSE_INSTANT_CASH);
        logger.info("Total Records: {} ", remittanceList.size());
        return remittanceList;
    }

    private List<String> getExistingReferences(String exchangeCode, List<String> references) {
        return remittanceDataService.findAllByExchangeCodeAndReferenceNumbers(exchangeCode, references);
    }

    private List<RemittanceData> filterValidRemittances(List<RemittanceData> remittanceList) {
        Predicate<RemittanceData> isDuplicateOrRejected = remittanceData -> remittanceData.isDuplicate() || remittanceData.getProcessStatus().equals(RemittanceData.REJECTED);
        return remittanceList.stream().filter(isDuplicateOrRejected.negate()).distinct().collect(Collectors.toList());
    }

    private ICOutstandingTransactionDTO mapDuplicate(ICOutstandingTransactionDTO dto, List<String> existingReferences) {
        if (existingReferences.contains(dto.getReference())) {
            dto.setDuplicate(true);
            dto.setProcessStatus(RemittanceData.REJECTED);
            dto.setReasonForInvalid(Constants.REFERENCE_NO_ALREADY_EXIST);
        }
        return dto;
    }

    private ResponseEntity<ICOutstandingRemittanceDTO> sendICOutstandingRemittanceDownloadRequest(ICExchangePropertyDTO icDTO, Long apiTraceId) {
        String outstandingUrl = icDTO.getOutstandingUrl() + "?pageNumber=1&pageSize=1000";
        HttpEntity<String> httpEntity = ApiUtil.createHttpEntity("", apiTraceId, icDTO);
        logger.info("ICOutstandingRemittance httpEntity: \n {}, \n OutstandingRequest URL: {}\n", convertObjectToString(httpEntity), outstandingUrl);
        return restTemplate.exchange(outstandingUrl, HttpMethod.GET, httpEntity, ICOutstandingRemittanceDTO.class);
    }

    private List<RemittanceData> removeStrace(List<RemittanceData> remittanceDataList, Long traceId) {
        apiTraceService.deleteById(traceId);
        logger.info(Constants.TRACING_REMOVED_BECAUSE_NO_RECORD_FOUND_TRACE_ID, "fetchICOutstandingRemittance()", traceId);
        return remittanceDataList;
    }


}
