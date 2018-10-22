package ru.naumen.sd40.log.parser.parsers.timeParsers;

import java.text.ParseException;

public interface ITimeParser {
    void configureTimeZone(String value);

    long parseTime(String line) throws ParseException;
}
