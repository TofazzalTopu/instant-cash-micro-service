package com.info.transaction.service;

import com.info.transaction.feignclient.InstantCashServiceFeignClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class HandleValidationErrorServiceDelegateTest {

    @Mock
    private InstantCashServiceFeignClient instantCashServiceFeignClient;

    @Mock
    private DelegateExecution delegateExecution;

    @InjectMocks
    private HandleValidationErrorService handleValidationErrorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_WhenValidationFailed_ShouldCancelPayment() throws Exception {
        // Arrange
        when(delegateExecution.getVariable("reference")).thenReturn("REF123");
        when(delegateExecution.getVariable("errorMessage")).thenReturn("Invalid Account");
        when(delegateExecution.getVariable("validationStatus")).thenReturn("FAILED");

        // Act
        handleValidationErrorService.execute(delegateExecution);

        // Assert
        verify(instantCashServiceFeignClient).updateRemittanceData("REF123", "OPEN");
        verify(delegateExecution).setVariable("cancelStatus", "CANCELLED");
    }

    @Test
    void testExecute_WhenValidationNotFailed_ShouldNotCancelPayment() throws Exception {
        // Arrange
        when(delegateExecution.getVariable("reference")).thenReturn("REF123");
        when(delegateExecution.getVariable("errorMessage")).thenReturn("Some Warning");
        when(delegateExecution.getVariable("validationStatus")).thenReturn("SUCCESS");

        // Act
        handleValidationErrorService.execute(delegateExecution);

        // Assert
        verify(instantCashServiceFeignClient, never()).updateRemittanceData(anyString(), anyString());
        verify(delegateExecution, never()).setVariable("cancelStatus", "CANCELLED");
    }
}

