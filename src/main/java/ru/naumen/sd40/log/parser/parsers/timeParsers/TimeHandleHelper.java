package ru.naumen.sd40.log.parser.parsers.timeParsers;

public class TimeHandleHelper {

    public static long prepareDate(long parsedDate)
    {
        int min5 = 5 * 60 * 1000;
        long count = parsedDate / min5;
        return count * min5;
    }
}
