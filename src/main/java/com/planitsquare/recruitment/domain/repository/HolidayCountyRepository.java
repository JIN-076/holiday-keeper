package com.planitsquare.recruitment.domain.repository;

import com.planitsquare.recruitment.domain.entity.HolidayCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayCountyRepository extends JpaRepository<HolidayCounty, Long> {

}
