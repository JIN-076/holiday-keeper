package com.planitsquare.recruitment.domain.repository;

import com.planitsquare.recruitment.domain.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>, HolidayRepositoryCustom {

}
