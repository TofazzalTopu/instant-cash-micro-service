package com.info.api.service;

import com.info.api.constants.Constants;
import com.info.api.dto.ic.ICExchangePropertyDTO;
import com.info.api.dto.ic.ICOutstandingRemittanceDTO;
import com.info.api.dto.ic.ICOutstandingTransactionDTO;
import com.info.api.entity.Branch;
import com.info.api.entity.MbkBrn;
import com.info.api.entity.RemittanceData;
import com.info.api.mapper.ICOutstandingRemittanceMapper;
import com.info.api.service.common.*;
import com.info.api.service.impl.ic.ICOutstandingRemittanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ICOutstandingRemittanceServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private BranchService branchService;

    @Mock
    private MbkBrnService mbkBrnService;

    @Mock
    private ApiTraceService apiTraceService;

    @Mock
    private RemittanceDataService remittanceDataService;

    @Mock
    private RemittanceProcessService remittanceProcessService;

    @Mock
    private ICOutstandingRemittanceMapper icRemittanceMapper;

    @InjectMocks
    private ICOutstandingRemittanceServiceImpl icOutstandingRemittanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchICOutstandingRemittance_Success() {
        ICExchangePropertyDTO icDTO = new ICExchangePropertyDTO();
        icDTO.setExchangeCode("EX123");
        icDTO.setOutstandingUrl("http://outstanding.url");

        // Mock API response
        ICOutstandingRemittanceDTO remittanceDTO = new ICOutstandingRemittanceDTO();
        ICOutstandingTransactionDTO transactionDTO = new ICOutstandingTransactionDTO();
        transactionDTO.setReference("REF123");
        remittanceDTO.setData(Collections.singletonList(transactionDTO));

        ResponseEntity<ICOutstandingRemittanceDTO> responseEntity = ResponseEntity.ok(remittanceDTO);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ICOutstandingRemittanceDTO.class)))
                .thenReturn(responseEntity);

        // Mock other service calls
        Branch branch = new Branch();
        branch.setRoutingNumber(12345);
        when(branchService.findAllByRoutingNumber(anyList())).thenReturn(Collections.singletonList(branch));

        MbkBrn mbkBrn = new MbkBrn();
        when(mbkBrnService.findAllByMbkbrnKeyBranchRoutingIn(anyList())).thenReturn(Collections.singletonList(mbkBrn));

        when(remittanceDataService.findAllByExchangeCodeAndReferenceNumbers(eq("EX123"), anyList())).thenReturn(Collections.emptyList());

        RemittanceData remittanceData = new RemittanceData();
        when(icRemittanceMapper.prepareRemittanceData(any(), eq("EX123"), any(), any(), any())).thenReturn(remittanceData);

        // Mock Remittance process saving
//        doNothing().when(remittanceProcessService).processAndSaveRemittanceData(anyList(), eq("EX123"), eq(Constants.EXCHANGE_HOUSE_INSTANT_CASH));

        // Call the method under test
        List<RemittanceData> result = icOutstandingRemittanceService.fetchICOutstandingRemittance(icDTO);

        // Validate the results
        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(apiTraceService, times(1)).create(anyString(), eq(Constants.REQUEST_TYPE_DOWNLOAD_REQ), any());
//        verify(apiTraceService, times(1)).saveApiTrace(any(), anyString(), anyString(), eq(Constants.API_STATUS_VALID));
//        verify(remittanceProcessService, times(1)).processAndSaveRemittanceData(anyList(), eq("EX123"), eq(Constants.EXCHANGE_HOUSE_INSTANT_CASH));
    }

    @Test
    void testFetchICOutstandingRemittance_Error() {
        ICExchangePropertyDTO icDTO = new ICExchangePropertyDTO();
        icDTO.setExchangeCode("EX123");
        icDTO.setOutstandingUrl("http://outstanding.url");

        // Mock API failure
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ICOutstandingRemittanceDTO.class)))
                .thenThrow(new RuntimeException("API call failed"));

        // Call the method under test
        List<RemittanceData> result = icOutstandingRemittanceService.fetchICOutstandingRemittance(icDTO);

        // Validate the results
        assertNotNull(result);
        assertTrue(result.isEmpty());
//        verify(apiTraceService, times(1)).create(anyString(), eq(Constants.REQUEST_TYPE_DOWNLOAD_REQ), any());
//        verify(apiTraceService, times(1)).saveApiTrace(any(), anyString(), anyString(), eq(Constants.API_STATUS_ERROR));
    }

    @Test
    void testFetchICOutstandingRemittance_NoData() {
        ICExchangePropertyDTO icDTO = new ICExchangePropertyDTO();
        icDTO.setExchangeCode("EX123");
        icDTO.setOutstandingUrl("http://outstanding.url");

        // Mock API response with no data
        ICOutstandingRemittanceDTO remittanceDTO = new ICOutstandingRemittanceDTO();
        remittanceDTO.setData(Collections.emptyList());
        ResponseEntity<ICOutstandingRemittanceDTO> responseEntity = ResponseEntity.ok(remittanceDTO);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ICOutstandingRemittanceDTO.class)))
                .thenReturn(responseEntity);

        // Call the method under test
        List<RemittanceData> result = icOutstandingRemittanceService.fetchICOutstandingRemittance(icDTO);

        // Validate the results
        assertNotNull(result);
        assertTrue(result.isEmpty());
//        verify(apiTraceService, times(1)).create(anyString(), eq(Constants.REQUEST_TYPE_DOWNLOAD_REQ), any());
//        verify(apiTraceService, times(1)).saveApiTrace(any(), anyString(), anyString(), eq(Constants.API_STATUS_VALID));
    }
}

