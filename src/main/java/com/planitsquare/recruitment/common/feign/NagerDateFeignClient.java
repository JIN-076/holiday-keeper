package com.planitsquare.recruitment.common.feign;

import java.util.List;

import com.planitsquare.recruitment.domain.dto.CountryDto;
import com.planitsquare.recruitment.domain.dto.HolidayDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 외부 API 호출을 위한 클라이언트로 다음 후보들을 고민
 *  1. RestTemplate (블로킹) -> Deprecated 예정
 *  2. OpenFeign (블로킹) -> 선언적 호출이 가능하고 직관적, 구성 및 사용이 쉬움 [채택]
 *  3. WebCLient (논블로킹) -> 대략 10,000건 정도의 데이터를 적재하는 과정에 적용하기엔 오버 엔지니어링 + 학습곡선이 크다고 판단
 */

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
