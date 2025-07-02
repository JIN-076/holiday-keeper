package com.planitsquare.recruitment.common.constant;

/**
 * Sql 전용 상수 클래스
 */

public final class BatchSql {

    private BatchSql() {}

    public static final String pipe = "|";

    public static final String UPSERT_COUNTRY = """
            MERGE INTO COUNTRY_TB (country_code, name) KEY (country_code) VALUES (?, ?)
        """;

    public static final String UPSERT_HOLIDAY = """
            MERGE INTO HOLIDAY_TB (date, fixed, global, country_code, local_name, name, type, launch_year)
                KEY (country_code, date, local_name)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;

    public static final String SELECT_HOLIDAY = """
            SELECT id, country_code, date, local_name FROM HOLIDAY_TB
                WHERE (country_code, date, local_name) IN (%s)
        """;

    public static final String UPSERT_COUNTY = """
            MERGE INTO HOLIDAY_COUNTY_TB (holiday_id, county) KEY (holiday_id, county) VALUES (?, ?);
        """;

    public static final String DELETE_COUNTY = """
            DELETE FROM HOLIDAY_COUNTY_TB
                         WHERE holiday_id IN (
                                SELECT id
                                FROM HOLIDAY_TB
                                WHERE global = TRUE
                         )
        """;

}
