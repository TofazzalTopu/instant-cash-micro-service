package com.info.bank.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CollectionAPIResponse<T> {

    private List<T> data;

}
