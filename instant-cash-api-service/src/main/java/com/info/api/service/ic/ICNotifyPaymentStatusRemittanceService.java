package com.info.api.service.ic;

import com.info.api.entity.RemittanceData;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.PaymentApiResponse;

import java.util.List;

public interface ICNotifyPaymentStatusRemittanceService {


   List<RemittanceData> notifyPaymentStatus(ICExchangePropertyDTO icExchangePropertyDTO);

    PaymentApiResponse notifyCashPaymentStatus(PaymentApiResponse paymentApiResponse, ICExchangePropertyDTO icDTO, String referenceNo);
}
