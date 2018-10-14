package ru.naumen.sd40.log.parser.parsers.timeParsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopTimeParser implements ITimeParser {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH:mm");
    private Pattern timeRegex = Pattern.compile("^_+ (\\S+)");
    private String dataDate;

    public TopTimeParser(String file)
    {
        Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(file);
        if (!matcher.find())
            throw new IllegalArgumentException();
        this.dataDate = matcher.group(0).replaceAll("-", "");
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
