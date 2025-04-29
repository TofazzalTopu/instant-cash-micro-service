package com.info.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BANK")
public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "BANKCD_CODE")
    private String bankCode;

    @Column(name = "BANKCD_NAME")
    private String bankName;

    @Column(name = "BANKCD_SWIFT_BIC_CODE")
    private String swiftCode;

    @Column(name = "BANKCD_LOCAL_BANK")
    private String localBank;

    @Column(name = "BANKCD_BK_TYPE")
    private String bankcdBkType;

    @Column(name = "BANKCD_MICR_CODE")
    private String bankcdMicrCode;

}
