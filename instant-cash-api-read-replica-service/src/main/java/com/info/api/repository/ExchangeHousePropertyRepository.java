package com.info.api.repository;

import com.info.api.entity.ExchangeHouseProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeHousePropertyRepository extends JpaRepository<ExchangeHouseProperty, Long> {


    Optional<ExchangeHouseProperty> findByExchangeCodeAndKeyLabel(String exchangeCode, String keyLabel);
    ExchangeHouseProperty findByKeyLabelEquals(String keyLabel);

    List<ExchangeHouseProperty> findAllByExchangeCode(String exchangeCode);

    @Modifying
    @Transactional
    @Query (value="LOAD DATA LOCAL INFILE 'E://Tofazzal//ADC//Documents//KT//API Client//properties.csv' INTO TABLE REM_EXC_HOUSE_PROP FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES", nativeQuery = true)
    public void loadBulkLoadData();
}
