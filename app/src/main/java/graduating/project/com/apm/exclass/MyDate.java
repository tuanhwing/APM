package graduating.project.com.apm.exclass;

import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tuan on 27/12/2017.
 */

public class MyDate {

    public static String getStringYearMonthDay(String time){
        if(time == null || time.length() <= 0) return "Date invalid!";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
            Log.d("error_parse_datetime", date.toString());

        } catch (ParseException e) {
            Log.d("error_parse_datetime",String.valueOf(e.getMessage()));
        }
        if(date == null) return "Date invalid!";
        return String.valueOf(DateFormat.format("dd-MM-yyyy", date));
    }

    public static String getStringYearMonthDayHMS(String time){
        if(time == null || time.length() <= 0) return "Date invalid!";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
            Log.d("error_parse_datetime", date.toString());

        } catch (ParseException e) {
            Log.d("error_parse_datetime",String.valueOf(e.getMessage()));
        }
        if(date == null) return "Date invalid!";
        return String.valueOf(DateFormat.format("dd-MM-yyyy HH:mm:ss", date));
    }

    public static String getStringYearMonthDayHMSZ(String time){
        if(time == null || time.length() <= 0) return "Date invalid!";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        try {
            date = format.parse(time);
            Log.d("error_parse_datetime", date.toString());

        } catch (ParseException e) {
            Log.d("error_parse_datetime",String.valueOf(e.getMessage()));
        }
        if(date == null) return "Date invalid!";
        return String.valueOf(DateFormat.format("dd-MM-yyyy HH:mm:ss", date));
    }
}
