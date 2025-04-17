package com.info.api.service;

import com.info.api.constants.Constants;
import com.info.api.dto.ic.ICConfirmDTO;
import com.info.api.dto.ic.ICConfirmResponseDTO;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.entity.ApiTrace;
import com.info.api.entity.RemittanceData;
import com.info.api.service.common.ApiTraceService;
import com.info.api.service.common.RemittanceDataService;
import com.info.api.service.impl.ic.ICConfirmPaidStatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ICConfirmPaidStatusServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApiTraceService apiTraceService;

    @Mock
    private RemittanceDataService remittanceDataService;

    @InjectMocks
    private ICConfirmPaidStatusServiceImpl icConfirmPaidStatusService;

    private ICExchangePropertyDTO icDTO;
    private RemittanceData remittanceData;
    private ApiTrace apiTrace;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        icDTO = new ICExchangePropertyDTO();
        icDTO.setNotifyRemStatusUrl("http://example.com");

        remittanceData = new RemittanceData();
        remittanceData.setReferenceNo("12345");
        remittanceData.setApiResponse("response");

        apiTrace = new ApiTrace();
        apiTrace.setId(1L);
    }

    @Test
    public void testNotifyPaidStatus_Success() {
        // Prepare the mock behavior
        when(remittanceDataService.findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(
                any(), anyInt(), anyString(), anyList())).thenReturn(Arrays.asList(remittanceData));

        when(apiTraceService.create(any(), anyString(), any())).thenReturn(apiTrace);

        ICConfirmDTO mockConfirmDTO = mock(ICConfirmDTO.class);
        HttpEntity<ICConfirmDTO> httpEntity = new HttpEntity<>(mockConfirmDTO);

        // Mock the restTemplate call to return a valid response
        ICConfirmResponseDTO mockResponse = new ICConfirmResponseDTO();
        when(restTemplate.postForEntity(eq("http://example.com"), eq(httpEntity), eq(ICConfirmResponseDTO.class)))
                .thenReturn(ResponseEntity.ok(mockResponse));

        // Call the method
        icConfirmPaidStatusService.notifyPaidStatus(icDTO);

        // Verify the interactions and assert the behavior
        verify(remittanceDataService, times(1)).findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(
                any(), anyInt(), anyString(), anyList());
        verify(apiTraceService, times(1)).create(any(), anyString(), any());
        verify(restTemplate, times(1)).postForEntity(eq("http://example.com"), eq(httpEntity), eq(ICConfirmResponseDTO.class));

        // Ensure that remittance data was updated
        assertEquals(Constants.MIDDLEWARE_PUSH_DONE, remittanceData.getMiddlewarePush());
    }

    @Test
    public void testNotifyPaidStatus_ErrorInNotify() {
        // Prepare the mock behavior for when an exception occurs during the API call
        when(remittanceDataService.findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(
                any(), anyInt(), anyString(), anyList())).thenReturn(Arrays.asList(remittanceData));

        when(apiTraceService.create(any(), anyString(), any())).thenReturn(apiTrace);

        // Simulate an exception from the restTemplate
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(ICConfirmResponseDTO.class)))
                .thenThrow(new RuntimeException("API call failed"));

        // Call the method
        icConfirmPaidStatusService.notifyPaidStatus(icDTO);

        // Verify the interactions
        verify(remittanceDataService, times(1)).findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(
                any(), anyInt(), anyString(), anyList());
        verify(apiTraceService, times(1)).create(any(), anyString(), any());
        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(ICConfirmResponseDTO.class));

        // Ensure that remittance data is marked as error after failed API call
        assertEquals(Constants.MIDDLEWARE_PUSH_ERROR, remittanceData.getMiddlewarePush());
    }

    @Test
    public void testNotifyPaidStatus_EmptyRemittanceData() {
        // Prepare the mock behavior for when there is no remittance data
        when(remittanceDataService.findAllByExchangeCodeAndMiddlewarePushAndFinalStatusAndSourceTypeOrProcessStatus(
                any(), anyInt(), anyString(), anyList())).thenReturn(Arrays.asList());

        // Call the method
        icConfirmPaidStatusService.notifyPaidStatus(icDTO);

        // Verify that no API calls are made when there is no remittance data
        verify(restTemplate, never()).postForEntity(anyString(), any(HttpEntity.class), eq(ICConfirmResponseDTO.class));
    }
}

