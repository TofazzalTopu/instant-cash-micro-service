package com.info.api.dto.ic;


import lombok.Data;

import java.io.Serializable;

@Data
public class ICUnlockErrorDetailsDTO implements Serializable {

    private static final long serialVersionUID = -7969298090989117693L;

    private String target;

    private String message;

}
