package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportRequestBody {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String bruserid;
    @NotBlank
    private String brcode;
    @NotBlank
    private String exchcode;
    @NotBlank
    private String fromDate;
    @NotBlank
    private String toDate;
    private int pageNumber;
    private int pageSize;


}
