package projeto.monitoramentoestoque.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {

    public static String converterCalendarParaString(Calendar dataCalendar) {
        SimpleDateFormat formatoData = new SimpleDateFormat( "dd/MM/yyyy" );
        String dataString = formatoData.format(dataCalendar.getTime());
        return dataString;
    }
}
