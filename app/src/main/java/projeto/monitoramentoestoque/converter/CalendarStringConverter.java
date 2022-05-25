package projeto.monitoramentoestoque.converter;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarStringConverter {
    SimpleDateFormat formatoData = new SimpleDateFormat( "dd/MM/yyyy" );

    @TypeConverter
    public String converterCalendarParaString(Calendar dataCalendar) {
        String dataString = formatoData.format(dataCalendar.getTime());
        return dataString;
    }

    public Calendar conveterStringParaCalendar (String dataString){
        Calendar dataCalendar = null;

        try {
            dataCalendar.setTime(formatoData.parse(dataString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataCalendar;
    }
}
