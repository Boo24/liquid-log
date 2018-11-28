package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class TopTimeParserCreator implements ITimeParserCreator<TopTimeParser> {

    @Override
    @RequestScope
    public TopTimeParser create() {
        return new TopTimeParser();
    }
}
