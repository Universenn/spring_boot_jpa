package com.example.springbootjpa1.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// 넣지않으면 기본 클래스 명으로 들어간다.
@DiscriminatorValue("M")
@Entity
@Getter @Setter
public class Movie extends Item {
    private String director;
    private String actor;
}
