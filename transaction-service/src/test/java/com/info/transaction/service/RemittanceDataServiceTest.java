package com.info.transaction.service;

import com.info.dto.account.PaymentDTO;
import com.info.dto.constants.Constants;
import com.info.dto.remittance.RemittanceDataDTO;
import com.info.transaction.feignclient.InstantCashReplicaServiceFeignClient;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemittanceDataServiceTest {

    @Mock
    private InstantCashReplicaServiceFeignClient instantCashReplicaServiceFeignClient;

    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private RemittanceDataService remittanceDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulFlow() throws Exception {
        // Arrange
        String reference = "REF001";
        RemittanceDataDTO remittanceDataDTO = new RemittanceDataDTO();
        remittanceDataDTO.setAmount(BigDecimal.valueOf(1000.00));
        remittanceDataDTO.setCurrencyCode("USD");
        remittanceDataDTO.setCreditorAccountNo("1234567890");
        remittanceDataDTO.setBranchRoutingNumber("001");

        when(execution.getVariable("reference")).thenReturn(reference);
        when(instantCashReplicaServiceFeignClient.findByReference(reference)).thenReturn(remittanceDataDTO);

        // Act
        remittanceDataService.execute(execution);

        // Assert
        ArgumentCaptor<PaymentDTO> paymentCaptor = ArgumentCaptor.forClass(PaymentDTO.class);

        verify(execution).setVariable(eq("paymentDTO"), paymentCaptor.capture());
        verify(execution).setVariable(eq("accountNumber"), eq("1234567890"));

        PaymentDTO payment = paymentCaptor.getValue();
        assertEquals("REF001", payment.getReference());
        assertEquals(1000.00, payment.getAmount());
        assertEquals("USD", payment.getCurrencyCode());
        assertEquals("1234567890", payment.getAccountNumber());
        assertEquals(1, payment.getBranchCode());
    }

    @Test
    void testExecute_RemittanceDataNotFound_ShouldThrowBpmnError() {
        // Arrange
        String reference = "MISSING_REF";
        when(execution.getVariable("reference")).thenReturn(reference);
        when(instantCashReplicaServiceFeignClient.findByReference(reference)).thenReturn(null);

        // Act + Assert
        BpmnError error = assertThrows(BpmnError.class, () -> remittanceDataService.execute(execution));

        assertEquals("ERROR_VALIDATE_REFERENCE", error.getErrorCode());
        assertTrue(error.getMessage().contains(Constants.REFERENCE_NOT_EXIST));

        verify(execution).setVariable(eq("validationStatus"), eq("FAILED"));
        verify(execution).setVariable(eq("errorMessage"), contains(reference));
    }

    @Test
    void testExecute_ExceptionWhileSettingVariables_ShouldThrowBpmnError() {
        // Arrange
        String reference = "EX_REF";
        RemittanceDataDTO remittanceDataDTO = new RemittanceDataDTO();
        remittanceDataDTO.setAmount(BigDecimal.valueOf(100));
        remittanceDataDTO.setCurrencyCode("USD");
        remittanceDataDTO.setCreditorAccountNo("ACC001");
        remittanceDataDTO.setBranchRoutingNumber("INVALID"); // This will throw NumberFormatException

        when(execution.getVariable("reference")).thenReturn(reference);
        when(instantCashReplicaServiceFeignClient.findByReference(reference)).thenReturn(remittanceDataDTO);

        // Act + Assert
        BpmnError error = assertThrows(BpmnError.class, () -> remittanceDataService.execute(execution));

        assertEquals("ERROR_VALIDATE_REFERENCE", error.getErrorCode());
        assertEquals("Transaction Posting failed!", error.getMessage());
        verify(execution).setVariable("errorMessage", Constants.TRANSACTION_POSTING_FAILED);
    }
}
