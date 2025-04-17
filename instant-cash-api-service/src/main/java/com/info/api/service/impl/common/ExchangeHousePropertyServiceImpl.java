package com.info.api.service.impl.common;

import com.info.api.entity.ExchangeHouseProperty;
import com.info.api.repository.ExchangeHousePropertyRepository;
import com.info.api.service.common.ExchangeHousePropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeHousePropertyServiceImpl implements ExchangeHousePropertyService {

    private final ExchangeHousePropertyRepository exchangeHousePropertyRepository;


    @Override
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
    public ExchangeHouseProperty findByKeyLabelEquals(String keyLabel) {
        return exchangeHousePropertyRepository.findByKeyLabelEquals(keyLabel);
    }

    @Override
    public void loadBulkLoadData() {
        exchangeHousePropertyRepository.loadBulkLoadData();
    }


}
