package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICOutstandingRemittanceDTO {

    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private List<ICOutstandingTransactionDTO> data;

}
