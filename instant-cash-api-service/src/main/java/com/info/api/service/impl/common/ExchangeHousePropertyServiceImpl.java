package com.info.api.service.impl.common;

import com.info.api.entity.ExchangeHouseProperty;
import com.info.api.repository.ExchangeHousePropertyRepository;
import com.info.api.service.common.ExchangeHousePropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.info.dto.constants.Constants.CACHE_NAME_EXCHANGE_HOUSE_PROPERTY;
import static com.info.dto.constants.Constants.CACHE_NAME_MBKBRN;

@Service
@RequiredArgsConstructor
public class ExchangeHousePropertyServiceImpl implements ExchangeHousePropertyService {

    private final ExchangeHousePropertyRepository exchangeHousePropertyRepository;


    @Override
    @Cacheable(value = CACHE_NAME_EXCHANGE_HOUSE_PROPERTY, key = "'all'")
    public List<ExchangeHouseProperty> findAll() {
        return exchangeHousePropertyRepository.findAll(Sort.by("exchangeCode").ascending());
    }

    @Override
    public Optional<ExchangeHouseProperty> findByExchangeCodeAndKeyLabel(String exchangeCode, String keyLabel) {
        return exchangeHousePropertyRepository.findByExchangeCodeAndKeyLabel(exchangeCode, keyLabel);
    }

    @Override
    public List<ExchangeHouseProperty> findAllByExchangeCode(String exchangeCode) {
        return exchangeHousePropertyRepository.findAllByExchangeCode(exchangeCode);
    }

    @Override
    @Cacheable(value = CACHE_NAME_EXCHANGE_HOUSE_PROPERTY, key = "#keyLabel")
    public ExchangeHouseProperty findByKeyLabelEquals(String keyLabel) {
        return exchangeHousePropertyRepository.findByKeyLabelEquals(keyLabel);
    }

    @Override
    public void loadBulkLoadData() {
        exchangeHousePropertyRepository.loadBulkLoadData();
    }


}
