package com.sangnk.core.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class UtilsDate {

	private static final Logger LOGGER = LoggerFactory.getLogger(UtilsDate.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	public static Date str2date(String dateStr, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);//"yyyy-MM-dd HH:mm"
		Date date = null;
		try {
		    if(StringUtils.isNotBlank(dateStr))
			    date = sdf.parse(dateStr);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return date;
	}

    public static String date2str(Date input, String oFormat) {
        String result = "";
        if (input != null) {
            try {
                DateFormat df = new SimpleDateFormat(oFormat);
                result = df.format(input);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static Date genDate(String date, boolean isFrom) throws Exception {
        try {
            Date result = null;
            if (StringUtils.isNotBlank(date)) {
                if (isFrom) {
                    result = str2date(date + " 00:00:00", "dd/MM/yyyy HH:mm:ss");
                } else {
                    result = str2date(date + " 23:59:59", "dd/MM/yyyy HH:mm:ss");
                }
            }
            return result;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public static Calendar lastDayOfWeek(Calendar calendar) {
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            cal.set(Calendar.DAY_OF_YEAR, ++day);
        }
        return cal;
    }

    public static String date3str(Date createTime) {
        //get day,month
        Calendar cal = Calendar.getInstance();
        cal.setTime(createTime);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;

        String result = "TH??NG " + month + " " + day;
        return result;
    }

    // ng??y hi???n t???i
    public int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    // th??ng hi???n t???i
    public String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month > 9 ? "" + month : "0" + month;
    }

    // n??m hi???n t???i
    public int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    // ng??y hi???n t???i
    public String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return (day > 9 ? day : "0" + day) + "/" + (month > 9 ? month : "0" + month) + "/" + year;
    }

    // ng??y theo date
    public String getCurrentDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return (day > 9 ? day : "0" + day) + "/" + (month > 9 ? month : "0" + month) + "/" + year;
    }

    public String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();
        return formatter.format(lastDayOfMonth);
    }

    public String getLastDayOfWeek() {
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        c.add(Calendar.DATE, 6);
        return df.format(c.getTime());
    }

    public String getFirstDayOfWeek() {
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(c.getTime());
    }

    public boolean isDate(Object obj, String _format) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(_format);
            format.format(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*get min day with month*/
    public static int minDay(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        int minDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        return minDay;
    }

    /*get max day with month*/
    public static int maxDay(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    /*get ng??y ?????u tu???n hi???n t???i*/
    public static Date firstDayWithMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.roll(Calendar.DATE, -(c.get(Calendar.DAY_OF_WEEK) - 1));    //tr??? ?????n ng??y ?????u tu???n
        Date date = c.getTime();
        return date;
    }

    /*get ng??y ?????u tu???n theo date*/
    public static Date firstDayWithMonth(Date date_) {
        Calendar c = Calendar.getInstance();
        c.setTime(date_);
        c.roll(Calendar.DATE, -(c.get(Calendar.DAY_OF_WEEK) - 1));    //tr??? ?????n ng??y ?????u tu???n
        Date date = c.getTime();
        return date;
    }

    // c???ng tr??? gi???
    public static Date hourDate(Date input, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }

    // c???ng tr??? ph??t
    public static Date minuteDate(Date input, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        c.add(Calendar.MINUTE, minute);
        return c.getTime();
    }

    // c???ng tr??? gi??y
    public static Date sencondDate(Date input, int sencond) {
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        c.add(Calendar.SECOND, sencond);
        return c.getTime();
    }

    // c???ng tr??? ng??y
    public static Date dayDate(Date input, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        c.roll(Calendar.DATE, day);
        return c.getTime();
    }

    // c???ng tr??? th??ng
    public static Date monthDate(Date input, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        c.roll(Calendar.MONTH, day);
        return c.getTime();
    }

    // c???ng tr??? n??m
    public static Date yearDate(Date input, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        c.roll(Calendar.YEAR, day);
        return c.getTime();
    }

    // date 1 l???n h??n date 2
    public static boolean compareDate(Date date1, Date date2) {
        return date1.after(date2);
    }

    //l???y s??? ng??y trong th??ng
    public static int getMaxDayWithMonth(int thang, int nam) {
        switch (thang)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isCheck(nam))
                    return 29;
                else
                    return 28;
            default:
                return 0;
        }
    }
    public static boolean isCheck(int nam) {
        if ((nam % 4 == 0 && nam % 100 != 0) || nam % 400 == 0)
            return true;
        return false;
    }

    // ?????m s??? ng??y gi???a 2 m???c th???i gian
    public static long daysBetween2Dates(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        return (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
    }

    // Ki???m tra c?? ph???i n??m nhu???n hay kh??ng
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
            return true;
        }
        return false;
    }

    // ?????m s??? ng??y trong m???t th??ng
    public static int daysInMonth(Date input) {
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int [] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
        daysInMonths[1] += isLeapYear(year) ? 1 : 0;  // S??? d???ng ph????ng th???c ki???m tra n??m nhu???n ??? tr??n
        return daysInMonths[c.get(Calendar.MONTH)];
    }

    // So s??nh gi??? hi???n t???i c?? n???m trong kho???ng gi??? n??o ???? trong n??y hay kh??ng
    public static boolean isInHappyHour(String startHour, Date midlle, String endHour) throws ParseException, ParseException {
        boolean result = false;
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        Date start = hourFormat.parse(startHour);
        Date end = hourFormat.parse(endHour);

        String nowHourStr = hourFormat.format(midlle.getTime());
        try {
            Date nowHour = hourFormat.parse(nowHourStr);
            if (nowHour.after(start) && nowHour.before(end) || (nowHour.equals(start) || (nowHour.equals(end)))) {
                result = true;
            }
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }

    // X??c ?????nh gi???a 2 kho???ng th???i gian c?? bao nhi??u gi???, bao nhi??u ph??t, bao nhi??u gi??y:
    public static void main(String[] args) {

        String dateStart = "2012-03-14 09:33:58";

        String dateStop = "2012-03-14 10:34:59";

        // Custom date format

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;

        Date d2 = null;

        try {

            d1 = format.parse(dateStart);

            d2 = format.parse(dateStop);

        } catch (ParseException e) {

        }

        // Get msec from each, and subtract.

        long diff = d2.getTime() - d1.getTime();

        long diffSeconds = diff / 1000;

        long diffMinutes = diff / (60 * 1000);

        long diffHours = diff / (60 * 60 * 1000);

        System.out.println("S??? gi??y : " + diffSeconds + " seconds.");

        System.out.println("S??? ph??t: " + diffMinutes + " minutes.");

        System.out.println("S??? gi???: " + diffHours + " hours.");

        //-----------------------------------------------------

        // LocalDate: m?? ta?? ki????u d???? li????u date chi?? bao g????m nga??y, tha??ng, n??m
        // Th??????ng ????????c s???? du??ng ?????? l??u tr????, m?? ta??: nga??y sinh, nga??y t????t nghi????p
        // hay nga??y va??o 1 c??ng ty ...
        // ????? s??? d???ng LocalDate ch??ng ta s??? import g??i th?? vi???n java.time.LocalDate c???a Java
        // LocalDate.of(2010, 1, 15) s??? tr??? v??? bi???n startDate c?? ng??y, th??ng, n??m l???n l?????t l??
        // 15, 1 v?? 2010
        LocalDate startDate = LocalDate.of(2010, 1, 15);

        LocalDate endDate = LocalDate.of(2011, 3, 18);

        // t??nh s??? ch??nh l???ch gi???a startDate v?? endDate
        // s??? d???ng h??m between() c???a Peridot
        // ????? s??? d???ng Peridot ch??ng ta s??? import g??i th?? vi???n java.time.Period c???a Java
        Period different = Period.between(startDate, endDate);

        System.out.println("S??? ch??nh l???ch gi???a startDate v?? endDate l?? " +
                different.getYears() + " n??m " + different.getMonths() + " th??ng v?? " +
                different.getDays() + " ng??y.");

    }

}
