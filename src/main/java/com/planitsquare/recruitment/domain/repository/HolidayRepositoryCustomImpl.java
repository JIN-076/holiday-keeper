package com.planitsquare.recruitment.domain.repository;

import static com.planitsquare.recruitment.domain.entity.QHoliday.holiday;
import static com.planitsquare.recruitment.domain.entity.QCountry.country;
import static com.planitsquare.recruitment.domain.entity.QHolidayCounty.holidayCounty;

import com.planitsquare.recruitment.common.base.CursorPaginationInfoReq;
import com.planitsquare.recruitment.common.base.CursorPaginationResult;
import com.planitsquare.recruitment.api.dto.HolidayInfoResponse;
import com.planitsquare.recruitment.domain.entity.enums.HolidayType;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HolidayRepositoryCustomImpl implements HolidayRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CursorPaginationResult<HolidayInfoResponse> findByConditionWithPagination(
            String year, String code, String type,
            LocalDate from, LocalDate to,
            CursorPaginationInfoReq pageable
    ) {
        List<Long> holidayIds = jpaQueryFactory
                .select(holiday.id)
                .from(holiday)
                .where(
                        cursorIdCondition(pageable.getCursorId()),
                        yearCondition(year),
                        countryCondition(code),
                        typeCondition(type),
                        fromToCondition(from, to)
                )
                .orderBy(holiday.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<HolidayInfoResponse> result = jpaQueryFactory
                .selectFrom(holiday)
                .join(country).on(holiday.country.countryCode.eq(country.countryCode))
                .leftJoin(holidayCounty).on(holiday.id.eq(holidayCounty.holiday.id))
                .where(holiday.id.in(holidayIds))
                .orderBy(holiday.id.desc())
                .transform(GroupBy.groupBy(holiday.id).list(
                        Projections.constructor(HolidayInfoResponse.class,
                                holiday.id,
                                holiday.date,
                                holiday.localName,
                                holiday.name,
                                country.name,
                                holiday.type,
                                GroupBy.list(holidayCounty.county)
                        )
                ));
        return CursorPaginationResult.fromDataWithExtraItemForNextCheck(result, pageable.getPageSize());
    }

    @Override
    public long deleteByCondition(String year, String code) {
        jpaQueryFactory.delete(holidayCounty)
                .where(holidayCounty.holiday.in(
                        JPAExpressions.selectFrom(holiday)
                                .where(
                                        yearCondition(year),
                                        countryCondition(code)
                                )
                )).execute();

        return jpaQueryFactory.delete(holiday)
                .where(
                        yearCondition(year),
                        countryCondition(code)
                ).execute();
    }

    private BooleanExpression cursorIdCondition(Long cursorId) {
        return cursorId != null ? holiday.id.lt(cursorId) : null;
    }

    private BooleanExpression yearCondition(String year) {
        if (year == null) return null;

        return Expressions.numberTemplate(
            Integer.class, "YEAR({0})", holiday.date
        ).eq(Integer.valueOf(year));
    }

    private BooleanExpression countryCondition(String countryCode) {
        return countryCode != null ? holiday.country.countryCode.eq(countryCode) : null;
    }

    private BooleanExpression typeCondition(String type) {
        return type != null ? holiday.type.eq(HolidayType.valueOf(type)) : null;
    }

    private BooleanExpression fromToCondition(LocalDate from, LocalDate to) {
        if (from == null || to == null) return null;

        return holiday.date.goe(from).and(holiday.date.loe(to));
    }
}
