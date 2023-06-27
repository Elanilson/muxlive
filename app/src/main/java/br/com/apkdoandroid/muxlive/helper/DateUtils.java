package br.com.apkdoandroid.muxlive.helper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String convertMillisToDateTime(String milisegundos) {
        Long millis = Long.parseLong(milisegundos);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.getDefault());
        Date date = new Date(millis);
        return sdf.format(date);
    }
}
