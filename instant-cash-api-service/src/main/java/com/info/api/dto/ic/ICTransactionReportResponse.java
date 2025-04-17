package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ICTransactionReportResponse {

    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private List<ICTransactionReportDTO> data;

}
