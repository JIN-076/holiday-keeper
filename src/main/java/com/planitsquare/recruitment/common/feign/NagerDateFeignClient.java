package com.planitsquare.recruitment.common.feign;

import java.util.List;

import com.planitsquare.recruitment.domain.dto.CountryDto;
import com.planitsquare.recruitment.domain.dto.HolidayDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(
    name = "nagerDateFeignClient",
    url = "https://date.nager.at/api/v3"
)
public interface NagerDateFeignClient {

    @GetMapping("/AvailableCountries")
    List<CountryDto> getAllAvailableCountries();

    @GetMapping("/PublicHolidays/{year}/{countryCode}")
    List<HolidayDto> getHolidaysByCondition(
        @PathVariable("year") int year,
        @PathVariable("countryCode") String countryCode
    );

}
