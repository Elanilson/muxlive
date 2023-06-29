package br.com.apkdoandroid.muxlive.helper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String convertMillisToDateTime(String seconds) {
        long timestamp = Long.parseLong(seconds) * 1000; // Converter segundos para milissegundos
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }
}
