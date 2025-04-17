package com.info.api.service.ic;

import com.info.api.dto.PaymentApiRequest;
import com.info.api.dto.PaymentApiResponse;
import com.info.api.dto.ic.ICConfirmDTO;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.entity.RemittanceData;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
import java.util.List;

public interface ICConfirmTransactionStatusService {

    List<RemittanceData> confirmOutstandingTransactionStatus(@NotBlank ICExchangePropertyDTO icExchangePropertyDTO, @NotEmpty List<RemittanceData> remittanceDataList);

    PaymentApiResponse confirmCahTransactionPayment(@NotBlank PaymentApiResponse paymentApiResponse, PaymentApiRequest paymentApiRequest, @NotBlank ICExchangePropertyDTO icDTO);

    PaymentApiResponse notifyPaymentStatus(PaymentApiResponse paymentApiResponse, @NotBlank ICExchangePropertyDTO icDTO, @NotBlank RemittanceData remittanceData, ICConfirmDTO icConfirmDTO, @NotBlank String referenceNo, boolean update);
}
