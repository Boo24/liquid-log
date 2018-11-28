package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class GcTimeParserCreator implements ITimeParserCreator<GcTimeParser> {

    @Override
    @RequestScope
    public GcTimeParser create() {
        return new GcTimeParser();
    }
}
