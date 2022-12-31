package com.example.springbootjpa1.domain.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

// jpa 내장 타입
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
