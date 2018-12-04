package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.stereotype.Component;

@Component
public class GcTimeParserCreator implements ITimeParserCreator<GcTimeParser> {

    @Override
    public GcTimeParser create() {
        return new GcTimeParser();
    }
}
