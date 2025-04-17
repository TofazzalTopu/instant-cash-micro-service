package com.info.api.dto.ic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICTransactionReportAgentDTO {

    @NotEmpty
    private String agentCorrespondentId;
    @NotEmpty
    private String agentName;
    private String agentUserId;
    private String agentBranchCode;

}
