package com.planitsquare.recruitment.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "holiday_county_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HolidayCounty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holiday_id")
    private Holiday holiday;

    @Column(nullable = false)
    private String county;

    private HolidayCounty(Long id, Holiday holiday, String county) {
        this.id = id;
        this.holiday = holiday;
        this.county = county;
    }

    public static HolidayCounty of(Long id, Holiday holiday, String county) {
        return new HolidayCounty(id, holiday, county);
    }
}
