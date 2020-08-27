package org.example.dc.srv.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetDateUtil {
    public static String getToday(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String today = simpleDateFormat.format(calendar.getTime());

        return today;
    }
    public static String getYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String yesterday = simpleDateFormat.format(calendar.getTime());

        return yesterday;
    }

}
