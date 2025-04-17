package com.info.api.dto.ic;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ICIdDetailsDTO {

    /**
     * Mandatory if receiving country is Tanzania.
     * The value will be displayed only to the requested partners.
     */
    private String type;

    /**
     * Mandatory if receiving country is Tanzania.
     * The value will be displayed only to the requested partners.
     */
    private String number;
    /**
     Format: ISO-3166 alpha-2 code
     */
    private String issueDate;
    /**
     Format: ISO-3166 alpha-2 code
     */
    private String expiryDate;
    /**
     Format: ISO-3166 alpha-2 code
     */
    private String placeOfIssue = "BD";
}
