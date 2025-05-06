package com.info.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeHousePropertyId implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String exchangeCode;

    private String keyLabel;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeHousePropertyId that = (ExchangeHousePropertyId) o;
        return Objects.equals(exchangeCode, that.exchangeCode) && Objects.equals(keyLabel, that.keyLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeCode, keyLabel);
    }
}
