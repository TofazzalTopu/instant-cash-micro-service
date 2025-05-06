package com.info.api.service.impl;

import com.info.api.entity.ExchangeHouseProperty;
import com.info.api.service.common.ExchangeHousePropertyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoadExchangeHouseProperty {

    private final ExchangeHousePropertyService exchangeHousePropertyService;

    @Getter
    public static List<ExchangeHouseProperty> exchangeHousePropertyList = new ArrayList<>();

    @PostConstruct
    public void exchangeHouseProperty() {
        exchangeHousePropertyList = exchangeHousePropertyService.findAll();
    }

    public static List<ExchangeHouseProperty> getICExchangeHouseProperty() {
        Predicate<ExchangeHouseProperty> icPredicate = e -> e.getKeyLabel().startsWith("IC_");
        return exchangeHousePropertyList.stream().filter(icPredicate).collect(Collectors.toList());
    }

    public static List<ExchangeHouseProperty> getRIAExchangeHouseProperty() {
        Predicate<ExchangeHouseProperty> icPredicate = e -> e.getKeyLabel().startsWith("RIA_");
        return exchangeHousePropertyList.stream().filter(icPredicate).collect(Collectors.toList());
    }
}
