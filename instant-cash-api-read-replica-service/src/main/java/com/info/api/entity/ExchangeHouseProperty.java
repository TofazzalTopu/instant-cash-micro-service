package com.info.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REM_EXC_HOUSE_PROP")
@IdClass(ExchangeHousePropertyId.class)
public class ExchangeHouseProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "EXCHANGE_CODE", nullable = false, length = 20)
    private String exchangeCode;

    @Id
    @Column(name = "KEY_LABEL", nullable = false, length = 32)
    private String keyLabel;

    @Column(name = "KEY_VALUE", nullable = false, length = 500)
    private String keyValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeHouseProperty that = (ExchangeHouseProperty) o;
        return Objects.equals(exchangeCode, that.exchangeCode) && Objects.equals(keyLabel, that.keyLabel) && Objects.equals(keyValue, that.keyValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeCode, keyLabel, keyValue);
    }

    @Override
    public String toString() {
        return "ExchangeHouseProperty{" +
                "exchangeCode='" + exchangeCode + '\'' +
                ", keyLabel='" + keyLabel + '\'' +
                ", keyValue='" + keyValue + '\'' +
                '}';
    }
}
