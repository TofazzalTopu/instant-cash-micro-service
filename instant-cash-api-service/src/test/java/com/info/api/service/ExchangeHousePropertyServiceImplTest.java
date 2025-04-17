package com.info.api.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.info.api.entity.ExchangeHouseProperty;
import com.info.api.repository.ExchangeHousePropertyRepository;
import com.info.api.service.impl.common.ExchangeHousePropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ExchangeHousePropertyServiceImplTest {

    @Mock
    private ExchangeHousePropertyRepository exchangeHousePropertyRepository;

    @InjectMocks
    private ExchangeHousePropertyServiceImpl exchangeHousePropertyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testFindAll() {
        // Mock data
        List<ExchangeHouseProperty> mockProperties = Arrays.asList(
                new ExchangeHouseProperty("exchangeCode", "IC_example_1", "value1"),
                new ExchangeHouseProperty("exchangeCode", "IC_example_2", "value2")
        );

        // Mock the repository call
        when(exchangeHousePropertyRepository.findAll(Sort.by("exchangeCode").ascending())).thenReturn(mockProperties);

        // Call the method under test
        List<ExchangeHouseProperty> result = exchangeHousePropertyService.findAll();

        // Verify results
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify the repository method was called with the correct parameters
        verify(exchangeHousePropertyRepository, times(1)).findAll(Sort.by("exchangeCode").ascending());
    }

    @Test
    public void testFindByExchangeCodeAndKeyLabel() {
        // Mock data
        ExchangeHouseProperty mockProperty = new ExchangeHouseProperty("exchangeCode","IC_example_1", "value1");

        // Mock the repository call
        when(exchangeHousePropertyRepository.findByExchangeCodeAndKeyLabel("IC", "example_1"))
                .thenReturn(Optional.of(mockProperty));

        // Call the method under test
        Optional<ExchangeHouseProperty> result = exchangeHousePropertyService.findByExchangeCodeAndKeyLabel("IC", "example_1");

        // Verify results
        assertTrue(result.isPresent());
    }

    @Test
    public void testFindAllByExchangeCode() {
        // Mock data
        List<ExchangeHouseProperty> mockProperties = Arrays.asList(
                new ExchangeHouseProperty("exchangeCode", "IC_example_1", "value1"),
                new ExchangeHouseProperty("exchangeCode", "IC_example_2", "value2")
        );

        // Mock the repository call
        when(exchangeHousePropertyRepository.findAllByExchangeCode("IC")).thenReturn(mockProperties);

        // Call the method under test
        List<ExchangeHouseProperty> result = exchangeHousePropertyService.findAllByExchangeCode("IC");

        // Verify results
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

