package liram.dev.cryptocurrency.utility;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UnixDateFormatter extends IndexAxisValueFormatter {

    private List<Long> unixDatesFormatter;
    private DateFormat dateFormatter;
    private Date date;

    public UnixDateFormatter(List<Long> unixDatesFormatter){
        this.unixDatesFormatter = unixDatesFormatter;
        dateFormatter = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        date = new Date();

    }

    @Override
    public String getFormattedValue(float value) {
        if ((int)value < 0 || value > unixDatesFormatter.size() - 1){
            return  "";
        }

        //fetch real date formatter:
        long originalTimestamp = unixDatesFormatter.get((int)value);

        //convert to date String
        return getDateString(originalTimestamp);
    }

    private String getDateString(long timestamp) {
        try {
            date.setTime(timestamp * 1000);
            return dateFormatter.format(date);
        } catch(Exception ex) {
            return "xx";
        }
    }

}
