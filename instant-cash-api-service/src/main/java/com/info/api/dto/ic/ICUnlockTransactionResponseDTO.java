package com.info.api.dto.ic;


import lombok.Data;

@Data
public class ICUnlockTransactionResponseDTO {

    private String reference;

    private ICUnlockTransactionErrorDTO error;


}
