package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICBankDetailsDTO {

    /**
     * Mandatory for delivery modes “Account Transfers”: 04 - “Account Transfer
     * To Other Bank”, 08 - ”Account Transfer To Same Bank”.
     */
    private String bankName;

    //Mandatory for delivery modes “Account Transfers”: 03 - “Credit To Same
//    Bank”, 04 - “Account Transfer To Other Bank”, 08 - ”Account Transfer To Same Bank”.
    private String bankAccountNumber;

    //Mandatory for delivery modes “Account Transfers”: 04 - “Account Transfer
//    To Other Bank”, 08 - ”Account Transfer To Same Bank”.
    private String bankAddress1;
    private String bankAddress2;

    /**
     * If the Beneficiary Bank is Allied Bank Limited and Instant Cash has
     * arrangements with JS Bank and Bank of Punjab.
     * Then while sending the transaction, the agent has to enter the paying agent
     * code as the Head Office code of either
     * (JS BANK or BANK OF PUNJAB) and the correspondent bank code number of ALLIED
     * BANK as mandatory.
     * • IBFT - In Case of Pakistan
     * • IFSC - In Case of India
     * • BEFTN - In Case of Bangladesh
     * Conditional rules:
     * • Mandatory if delivery mode is 04 - “Account Transfer To Other Bank”.
     */
    private String bankCode;
}
