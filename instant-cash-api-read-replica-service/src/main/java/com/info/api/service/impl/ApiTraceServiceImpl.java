package com.info.api.service.impl;


import com.info.api.entity.ApiTrace;
import com.info.api.repository.ApiTraceRepository;
import com.info.api.service.common.ApiTraceService;
import com.info.api.util.DateUtil;
import com.info.api.util.ObjectConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.info.dto.constants.Constants.CACHE_NAME_API_TRACE;


@Service
@RequiredArgsConstructor
public class ApiTraceServiceImpl implements ApiTraceService {
    private static final Logger logger = LoggerFactory.getLogger(ApiTraceServiceImpl.class);

    private final ApiTraceRepository apiTraceRepository;

    @Override
    @Cacheable(value = CACHE_NAME_API_TRACE, key = "#apiTraceId")
    public Optional<ApiTrace> findById(Long apiTraceId) {
        return apiTraceRepository.findById(apiTraceId);
    }

    @Override
    public void deleteById(long id) {
        apiTraceRepository.deleteById(id);
    }


    public Date getCurrentBusinessDate() {
        Timestamp date = apiTraceRepository.getCurrentBusinessDate();
        return DateUtil.convertTimestampToCalendar(date).getTime();
    }

    @Override
    @Cacheable(value = CACHE_NAME_API_TRACE, key = "T(java.util.Objects).hash(#tranNo, #requestType)")
    public Optional<ApiTrace> findByTranNo(String tranNo, String requestType) {
        return apiTraceRepository.findByTranNo(tranNo, requestType);
    }

    @Override
    public Timestamp getRefDate(String exchangeCode, String pin, Date tranDate) {
        return apiTraceRepository.getRefDate(exchangeCode, pin, tranDate);
    }

    @Override
    public Long getApiTranSequence() {
        return apiTraceRepository.getApiTranSequence();
    }


    @Override
    public <T> ApiTrace buildApiTrace(ApiTrace trace, String referenceNo, T response, String status) {
        try {
            if (Objects.isNull(trace)) {
                trace = new ApiTrace();
            }
            if (Objects.nonNull(referenceNo)) trace.setRequestMsg(referenceNo);

            if (Objects.nonNull(response)) {
                trace.setResponseMsg(ObjectConverter.convertObjectToString(response));
            }
            trace.setStatus(status);
        } catch (Exception e) {
            logger.error("Error occurred on processConfirmedRemittanceData buildApiTrace", e);
        }
        return trace;
    }

    @Override
    public List<Long> getCancelIds(int tryCount) {
        return apiTraceRepository.getCancelIds(tryCount);
    }


}
