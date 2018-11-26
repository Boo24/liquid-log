package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SdngTimeParser implements ITimeParser {

    private static final Pattern TIME_PATTERN = Pattern
            .compile("^\\d+ \\[.*?\\] \\((\\d{2} .{3} \\d{4} \\d{2}:\\d{2}:\\d{2},\\d{3})\\)");

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss,SSS",
            new Locale("ru", "RU"));

    public SdngTimeParser(){
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void configureTimeZone(String value) {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(value));
    }

    @Override
    public long parseTime(String line) throws ParseException
    {
        Matcher matcher = TIME_PATTERN.matcher(line);
        if (matcher.find())
        {
            String timeString = matcher.group(1);
            Date recDate = DATE_FORMAT.parse(timeString);
            return recDate.getTime();
        }
        return 0L;
    }
}
