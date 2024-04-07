package util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class TimeUtils {
    public static LocalDate convertToLocalDateViaSqlDate(Date sqlDate) {
        return new java.sql.Date(sqlDate.getTime()).toLocalDate();
    }
}
