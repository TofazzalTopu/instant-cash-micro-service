package com.info.api.service.impl.ic;

import com.info.api.entity.ApiTrace;
import com.info.api.entity.RemittanceData;
import com.info.api.dto.ic.ICConfirmDTO;
import com.info.api.dto.ic.ICConfirmResponseDTO;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.mapper.ICConfirmDTOMapper;
import com.info.api.service.common.ApiTraceService;
import com.info.api.service.common.RemittanceDataService;
import com.info.api.service.ic.ICConfirmPaidStatusService;
import com.info.api.util.ApiUtil;
import com.info.api.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.info.api.constants.RemittanceDataStatus.NEW_STATUS_Y;
import static com.info.api.entity.RemittanceData.COMPLETED;
import static com.info.api.util.ObjectConverter.convertObjectToString;

@Service
@RequiredArgsConstructor
public class ICConfirmPaidStatusServiceImpl implements ICConfirmPaidStatusService {

    public static final Logger logger = LoggerFactory.getLogger(ICConfirmPaidStatusServiceImpl.class);

    private final RestTemplate restTemplate;
    private final ApiTraceService apiTraceService;
    private final RemittanceDataService remittanceDataService;

    @Override
    public void notifyPaidStatus(ICExchangePropertyDTO icDTO) {
        if (ApiUtil.isInvalidICProperties(icDTO, icDTO.getNotifyRemStatusUrl())) {
            logger.error(Constants.EXCHANGE_HOUSE_PROPERTY_NOT_EXIST_FOR_NOTIFY_STATUS);
            return;
        }

        List<ApiTrace> apiTraceList = new ArrayList<>();
        List<RemittanceData> remittanceDataList = remittanceDataService.findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(icDTO.getExchangeCode(), 0, Constants.API_SOURCE_TYPE, Arrays.asList(COMPLETED))
                .stream().distinct().collect(Collectors.toList());
        try {
            remittanceDataList.forEach(rem -> {
                String request = "";
                String response = "";
                String apiStatus = Constants.API_STATUS_VALID;
                final ApiTrace apiTrace = apiTraceService.create(icDTO.getExchangeCode(), Constants.REQUEST_TYPE_NOTIFY_REM_STATUS, null);
                try {
                    if (Objects.nonNull(apiTrace)) {
                        ICConfirmDTO icConfirmDTO = ICConfirmDTOMapper.mapICConfirmDTO(rem, NEW_STATUS_Y, "Paid");
                        request = convertObjectToString(icConfirmDTO);

                        HttpEntity<ICConfirmDTO> httpEntity = ApiUtil.createHttpEntity(icConfirmDTO, apiTrace.getId(), icDTO);
                        ResponseEntity<ICConfirmResponseDTO> responseEntity = restTemplate.postForEntity(icDTO.getNotifyRemStatusUrl(), httpEntity, ICConfirmResponseDTO.class);
                        response = convertObjectToString(responseEntity.getBody());
                        rem.setApiResponse(response);

                        mapNotifiedICRemittanceData(responseEntity, rem);
                    }
                } catch (Exception e) {
                    apiStatus = Constants.API_STATUS_INVALID;
                    response = e.getMessage();
                    logger.error("Error in notify for ReferenceNo: {}", rem.getReferenceNo());
                }
                logger.info("\nRequest: {}, \nResponse: {}", request, response);
                apiTraceList.add(apiTraceService.buildApiTrace(apiTrace, request, response, apiStatus));
            });

            if (!remittanceDataList.isEmpty()) remittanceDataService.saveAll(remittanceDataList);

            if (!apiTraceList.isEmpty()) apiTraceService.saveAllApiTrace(apiTraceList);

        } catch (Exception e) {
            logger.error("Error in notify: {}", e.getMessage());
        }
    }

    private void mapNotifiedICRemittanceData(ResponseEntity<ICConfirmResponseDTO> responseEntity, RemittanceData remittanceData) {
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && Objects.nonNull(responseEntity.getBody())) {
            remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_DONE);
        } else {
            if (remittanceData.getTryCount() >= Constants.TRY_COUNT) {
                remittanceData.setMiddlewarePush(Constants.MIDDLEWARE_PUSH_ERROR);
            } else {
                remittanceData.setTryCount(remittanceData.getTryCount() + 1);
            }
        }
    }

}
