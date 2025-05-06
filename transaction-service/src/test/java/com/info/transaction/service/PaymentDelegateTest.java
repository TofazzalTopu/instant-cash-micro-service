package com.info.transaction.service;


import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import com.info.transaction.feignclient.AccountServiceFeignClient;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentDelegateTest {

    @Mock
    private AccountServiceFeignClient accountServiceFeignClient;

    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulPayment() {
        // Arrange
        PaymentDTO paymentDTO = new PaymentDTO();
        AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
        String reference = "TXN123";
        String accountNumber = "ACC456";
        String userId = "USER789";

        when(execution.getVariable("reference")).thenReturn(reference);
        when(execution.getVariable("accountNumber")).thenReturn(accountNumber);
        when(execution.getVariable("paymentDTO")).thenReturn(paymentDTO);
        when(execution.getVariable("userId")).thenReturn(userId);

        when(accountServiceFeignClient.updateAccountBalance(paymentDTO)).thenReturn(accountBalanceDTO);

        // Act
        assertDoesNotThrow(() -> paymentService.execute(execution));

        // Assert
        verify(accountServiceFeignClient).updateAccountBalance(paymentDTO);
        verify(execution).setVariable("paymentStatus", "SUCCESS");
    }

    @Test
    void testExecute_MissingVariables_ThrowsBpmnError() {
        when(execution.getVariable("reference")).thenReturn(null);
        when(execution.getVariable("paymentDTO")).thenReturn(null);
        when(execution.getVariable("userId")).thenReturn("USER789");

        BpmnError exception = assertThrows(BpmnError.class, () -> paymentService.execute(execution));

        assertEquals("ERROR_FINALIZE_PAYMENT", exception.getErrorCode());
        assertEquals("Missing required process variables.", exception.getMessage());
    }

    @Test
    void testExecute_UpdateAccountBalanceFails_ThrowsBpmnError() {
        PaymentDTO paymentDTO = new PaymentDTO();
        when(execution.getVariable("reference")).thenReturn("TXN123");
        when(execution.getVariable("accountNumber")).thenReturn("ACC456");
        when(execution.getVariable("paymentDTO")).thenReturn(paymentDTO);
        when(execution.getVariable("userId")).thenReturn("USER789");

        when(accountServiceFeignClient.updateAccountBalance(paymentDTO)).thenThrow(new RuntimeException("Service unavailable"));

        BpmnError exception = assertThrows(BpmnError.class, () -> paymentService.execute(execution));

        assertEquals("ERROR_FINALIZE_PAYMENT", exception.getErrorCode());
        assertEquals("Account Balance Update Failed!", exception.getMessage());
        verify(execution).setVariable("paymentStatus", "FAILED");
    }
}
