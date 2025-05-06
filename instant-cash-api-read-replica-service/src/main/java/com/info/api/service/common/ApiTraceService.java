package com.info.api.service.common;

import com.info.api.entity.ApiTrace;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ApiTraceService {

    Date getCurrentBusinessDate();

    Optional<ApiTrace> findById(Long apiTraceId);

    Optional<ApiTrace> findByTranNo(String tranNo, String requestType);

    Timestamp getRefDate(String exchangeCode, String pin, Date tranDate);

    Long getApiTranSequence();

    void deleteById(long id);

    <T> ApiTrace buildApiTrace(ApiTrace apiTrace, String referenceNo, T response, String status);

    List<Long> getCancelIds(int tryCount);

}
