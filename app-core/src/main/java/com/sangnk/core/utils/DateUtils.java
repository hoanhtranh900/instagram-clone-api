package com.sangnk.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    Calendar calendar;

    public DateUtils(Date date) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(date);
    }

    public static DateUtils of(Date date) {
        return new DateUtils(date);
    }

    public static DateUtils of(Long time) {
        return new DateUtils(new Date(time));
    }

    public static Date truncDate(Date date) {
        return date != null ? DateUtils.of(date).truncDate().toDate() : null;
    }

    public static Date truncMinute(Date date) {
        return date != null ? DateUtils.of(date).truncMinute().toDate() : null;
    }

    public static String convertDateToString(Date fromDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return simpleDateFormat.format(fromDate);
    }
    public static String convertDateToStringWithType(Date fromDate, String type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        return simpleDateFormat.format(fromDate);
    }
    public DateUtils addDate(int amount) {
        this.calendar.add(Calendar.DAY_OF_WEEK, amount);
        return this;
    }

    public DateUtils addMinutes(int amount) {
        this.calendar.add(Calendar.MINUTE, amount);
        return this;
    }

    public DateUtils truncDate() {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return this;
    }
    
    public DateUtils endDate() {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return this;
    }

    public DateUtils truncMinute() {
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return this;
    }

    public String format(String format) {
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public Date toDate() {
        return this.calendar.getTime();
    }

    public DateUtils tomorrow() {
        return truncDate().addDate(1);
    }
}
