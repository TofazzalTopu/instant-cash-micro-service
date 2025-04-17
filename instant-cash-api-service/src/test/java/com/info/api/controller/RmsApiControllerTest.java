package com.info.api.controller;

import com.info.api.aspect.ExceptionHandlingAspect;
import com.info.api.aspect.SecureLoginAspect;
import com.info.api.dto.SearchApiRequest;
import com.info.api.dto.ic.TransactionReportRequestBody;
import com.info.api.exception.GlobalExceptionHandler;
import com.info.api.repository.ExchangeHousePropertyRepository;
import com.info.api.service.common.ExchangeHousePropertyService;
import com.info.api.service.impl.common.ApiService;
import com.info.api.service.impl.common.LoadExchangeHouseProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class RmsApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ApiService apiService;

    @InjectMocks
    private RmsApiController rmsApiController;

    @Mock
    private SecureLoginAspect secureLoginAspect;

    @Mock
    private ExceptionHandlingAspect exceptionHandlingAspect;

    @Mock
    private ExchangeHousePropertyService exchangeHousePropertyService;

    @Mock
    private ExchangeHousePropertyRepository exchangeHousePropertyRepository;

    @Mock
    private LoadExchangeHouseProperty loadExchangeHouseProperty;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(rmsApiController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Add this line
                .build();
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testSearchRemittance_success() throws Exception {
        String mockResponse = "remittance data";
        SearchApiRequest searchApiRequest = new SearchApiRequest("bruserid", "brcode", "exchcode", "pinno", null);
        when(apiService.searchRemittance(any(SearchApiRequest.class), any())).thenReturn(mockResponse);

        mockMvc.perform(get("/apiservice/remittance")
                        .header("userId", "user123")
                        .header("password", "pass123")
                        .param("bruserid", "bruserid")
                        .param("brcode", "brcode")
                        .param("exchcode", "exchcode")
                        .param("pinno", "pinno"))
                .andExpect(status().isOk())
                .andExpect(content().string(mockResponse));

        verify(apiService, times(1)).searchRemittance(any(SearchApiRequest.class), any());
    }

    @Test
    void testSearchRemittance_noData() throws Exception {
        when(apiService.searchRemittance(any(SearchApiRequest.class), any())).thenReturn(null);

        mockMvc.perform(get("/apiservice/remittance")
                        .header("userId", "user123")
                        .header("password", "pass123")
                        .param("bruserid", "bruserid")
                        .param("brcode", "brcode")
                        .param("exchcode", "exchcode")
                        .param("pinno", "pinno"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No data found"));

        verify(apiService, times(1)).searchRemittance(any(SearchApiRequest.class), any());
    }

     @Test
    void testPayRemittance() throws Exception {
        String mockResponse = "payment success";
        String data = "{\"key\":\"value\"}";
        when(apiService.payRemittance(eq(data), any())).thenReturn(mockResponse);

        mockMvc.perform(put("/apiservice/remittance")
                        .header("userId", "user123")
                        .header("password", "pass123")
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(content().string(mockResponse));

        verify(apiService, times(1)).payRemittance(eq(data), any());
    }

     @Test
    void testTransactionReport() throws Exception {
        String mockReport = "transaction report data";
        TransactionReportRequestBody reportRequestBody = new TransactionReportRequestBody("user123", "pass123", null, null, "exchcode", "2025-01-01", "2025-12-31", 1, 10);
        when(apiService.fetchTransactionReport(eq(reportRequestBody))).thenReturn(mockReport);

        mockMvc.perform(get("/apiservice/transaction-report")
                        .header("userId", "user123")
                        .header("password", "pass123")
                        .param("exchcode", "exchcode")
                        .param("fromDate", "2025-01-01")
                        .param("toDate", "2025-12-31")
                        .param("pageNumber", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string(mockReport));

        verify(apiService, times(1)).fetchTransactionReport(eq(reportRequestBody));
    }

     @Test
    void testSearchRemittance_missingUserIdHeader() throws Exception {
        mockMvc.perform(get("/apiservice/remittance")
                        .header("password", "pass123")
                        .param("bruserid", "bruserid")
                        .param("brcode", "brcode")
                        .param("exchcode", "exchcode")
                        .param("pinno", "pinno"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.userId").value("must not be blank"));
    }

     @Test
    void testTransactionReport_missingExchcode() throws Exception {
        mockMvc.perform(get("/apiservice/transaction-report")
                        .header("userId", "user123")
                        .header("password", "pass123")
                        .param("fromDate", "2025-01-01")
                        .param("toDate", "2025-12-31")
                        .param("pageNumber", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exchcode").value("must not be blank"));
    }

     @Test
    void testTransactionReport_invalidPageNumber() throws Exception {
        mockMvc.perform(get("/apiservice/transaction-report")
                        .header("userId", "user123")
                        .header("password", "pass123")
                        .param("exchcode", "exchcode")
                        .param("fromDate", "2025-01-01")
                        .param("toDate", "2025-12-31")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.pageNumber").value("must be greater than or equal to 1"));
    }

     @Test
    void testSearchRemittance_serviceThrowsException() throws Exception {
        when(apiService.searchRemittance(any(), any())).thenThrow(new RuntimeException("Service Error"));

        mockMvc.perform(get("/apiservice/remittance")
                        .header("userId", "user123")
                        .header("password", "pass123")
                        .param("bruserid", "bruserid")
                        .param("brcode", "brcode")
                        .param("exchcode", "exchcode")
                        .param("pinno", "pinno"))
                .andExpect(status().isInternalServerError());
    }
}

