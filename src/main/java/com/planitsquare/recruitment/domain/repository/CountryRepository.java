package com.planitsquare.recruitment.domain.repository;

import com.planitsquare.recruitment.domain.entity.Country;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    @Query("select c from Country c where c.countryCode = :countryCode")
    Optional<Country> findByCountryCode(String countryCode);
}
