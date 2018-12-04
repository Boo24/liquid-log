package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TopTimeParser implements ITimeParser {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH:mm");
    private Pattern timeRegex = Pattern.compile("^_+ (\\S+)");
    private String dataDate;


    public void setDataDate(String dataDate){
        this.dataDate = dataDate;
    }

    @Override
    public void configureTimeZone(String value) {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(value));
    }

    @Override
    public long parseTime(String line) throws ParseException {
        long time = 0;
        Matcher matcher = timeRegex.matcher(line);
        if (matcher.find())
            return simpleDateFormat.parse(dataDate + matcher.group(1)).getTime();
        return time;
    }

}
