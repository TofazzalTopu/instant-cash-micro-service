package com.info.user.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Md. Tofazzal Hossain\nDate : 01-04-2025
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailData implements Serializable {

    private static final long serialVersionUID = 1744050117179344127L;

    private String appName;
    private String email;
    private String message;

}
