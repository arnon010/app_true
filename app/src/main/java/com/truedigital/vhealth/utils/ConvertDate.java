package com.truedigital.vhealth.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {

    public static Date convertStringToDate(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static Date convertDateFormat(Date date) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("dd/MM/yyyy");

        try {
            date = formatBefore.parse(formatBefore.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date StrToDateFormat(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static String convertStringAppointmentTime(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat formatAfter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String StringDateServiceFormat(String strDate) {
        //2018-05-10T00:20:00
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd MMM yyyy HH:mm");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String StringDateServiceFormatDate(String strDate) {
        //2018-05-10T00:20:00
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd MMM yyyy");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String StringDateServiceFormatFullTime(String strDate) {
        //2018-05-10T00:20:00
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatAfter = new SimpleDateFormat("HH:mm:ss");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String StringDateServiceFormatTime(String strDate) {
        //2018-05-10T00:20:00
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatAfter = new SimpleDateFormat("HH:mm");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static Date convertStrToDate(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static long dateToMillis(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
        Date dateTo = StrToDateFormat(strDate);
        long mills = dateTo.getTime();
        return (mills < 0L) ? 0 : mills;
    }

    public static long NearTime(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
        Date dateTo = StrToDateFormat(strDate);
        Date dateStart = new Date();
        long mills = dateTo.getTime() - dateStart.getTime();
        return (mills < 0L) ? 0 : mills;
    }

    public static String convertStringDateServiceFormat(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd MMM yyyy");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String convertDateToServiceFormat(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return formatAfter.format(date);
    }

    public static String getHour(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("HH");
        return formatAfter.format(date);
    }

    public static String getMinute(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("mm");
        return formatAfter.format(date);
    }

    public static String convertStringToServer(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat formatAfter = new SimpleDateFormat("MM-dd-yyyy");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String convertStringDateTimeToServer2(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat formatAfter = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String convertStringDateServiceShow(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd MMMM yyyy");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String convertStringShow(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd MMMM yyyy");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String convertDateSimpleShow(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd MMMM yyyy");
        return formatAfter.format(date);
    }

    public static String convertDateMonthYearShow(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("MMMM yyyy");
        return formatAfter.format(date);
    }

    public static String convertDateShow(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        return formatAfter.format(date);
    }

    public static String convertDateServer(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("yyyy-MM-dd");
        return formatAfter.format(date);
    }

    public static String convertDateShow(String strDate) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String convertDateSimpleShow(String strDate) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd/MM/yyyy");
        return formatAfter.format(date);
    }

    public static String convertDateToStringServer(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("MM-dd-yyyy");
        return formatAfter.format(date);
    }

    public static String convertStringServerToString(String dateStr) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatBefore.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static Date convertStringFromServerToDate(String strDate) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = formatAfter.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static String convertStringFromServerToHourStr(String strDate) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatAfter = new SimpleDateFormat("HH:mm");

        Date date = null;
        try {
            date = formatBefore.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatAfter.format(date);
    }

    public static String getDayOfWeek(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("E");
        return formatAfter.format(date);
    }

    public static String convertTimeToString(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("HH:mm");
        return formatAfter.format(date);
    }

    //use in chat
    public static String convertTimeToStringInCall(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("HH:mm:ss");
        return formatAfter.format(date);
    }

    //use in chat
    public static Date convertTimeFormat(Date date) {
        SimpleDateFormat formatBefore = new SimpleDateFormat("HH:mm");

        try {
            date = formatBefore.parse(formatBefore.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    //use in chat
    public static Date convertStringToTimeFormat(String strTime) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(strTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static String convertTimeStringToTimeFormat(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return format.format(date);
    }

    //..HH:MM:SS
    public static String getMinute(String strTime) {
        String[] timeArray = strTime.split(":");
        //int HH = Integer.parseInt(timeArray[0]);
        //int mm = Integer.parseInt(timeArray[1]);
        //int ss = Integer.parseInt(timeArray[2]);
        return ""+timeArray[1];
    }

    public static String convertDateNewLineYear(Date date) {
        SimpleDateFormat formatAfter = new SimpleDateFormat("d MMM\nyyyy");
        return formatAfter.format(date);
    }

}
