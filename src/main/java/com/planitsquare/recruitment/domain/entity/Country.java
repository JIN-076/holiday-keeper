package com.planitsquare.recruitment.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "country_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country {

    @Id
    @Column(columnDefinition = "CHAR(3)", nullable = false)
    private String countryCode;

    @Column(columnDefinition = "VARCHAR(100)", unique = true, nullable = false)
    private String name;

    private Country(String countryCode, String name) {
        this.countryCode = countryCode;
        this.name = name;
    }

    public static Country of(String countryCode, String name) {
        return new Country(countryCode, name);
    }

}
