package com.planitsquare.recruitment.domain.entity;

import com.planitsquare.recruitment.domain.entity.enums.HolidayType;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
    name = "holiday_tb",
    uniqueConstraints = @UniqueConstraint(
            name = "uk_holiday_unique",
            columnNames = {"country_code", "date", "local_name"}
    )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code", nullable = false)
    private Country country;

    @Column(nullable = false)
    private String localName;

    @Column(nullable = false)
    private String name;

    private boolean fixed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HolidayType type;

    private boolean global;

    private Integer launchYear;

    @Builder
    private Holiday(
            Country country, LocalDate date, String localName, String name,
            boolean fixed, HolidayType type, boolean global, Integer launchYear
    ) {
        this.country = country;
        this.date = date;
        this.localName = localName;
        this.name = name;
        this.fixed = fixed;
        this.type = type;
        this.global = global;
        this.launchYear = launchYear;
    }

    public static Holiday of(
            Country country, LocalDate date, String localName, String name,
            boolean fixed, HolidayType type, boolean global, Integer launchYear
    ) {
        return Holiday.builder()
                .country(country)
                .date(date)
                .localName(localName)
                .name(name)
                .fixed(fixed)
                .type(type)
                .global(global)
                .launchYear(launchYear)
                .build();
    }
}
