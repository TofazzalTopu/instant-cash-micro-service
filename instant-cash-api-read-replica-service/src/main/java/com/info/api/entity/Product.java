package com.info.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PRODUCT_TEST")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_TEST_SEQ")
    @SequenceGenerator(name = "PRODUCT_TEST_SEQ", sequenceName = "PRODUCT_TEST_SEQ", allocationSize = 20)
    private Long id;
    private String name;
    private String description;
    private Date createDate;

}
