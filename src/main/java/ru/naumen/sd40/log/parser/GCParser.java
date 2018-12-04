package ru.naumen.sd40.log.parser;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GCParser
{
    private static Pattern gcExecutionTime = Pattern.compile(".*real=(.*)secs.*");

    public static double parseLine(String line)
    {
        Matcher matcher = gcExecutionTime.matcher(line);
        if (matcher.find())
        {
            return Double.parseDouble(matcher.group(1).trim().replace(',', '.'));
        }
        return 0;
    }
}
