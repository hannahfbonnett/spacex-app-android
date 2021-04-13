package com.example.mobdevspacexapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeConverter {

    /*
        Format a unix date.
     */
    public static String getFormattedUnixDateTime(Long dateTimeUnix) {
        String formattedDateTime = "";
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        newFormat.setTimeZone(Calendar.getInstance().getTimeZone());
        formattedDateTime = newFormat.format(new Date(dateTimeUnix * 1000));

        return  formattedDateTime;
    }
}
