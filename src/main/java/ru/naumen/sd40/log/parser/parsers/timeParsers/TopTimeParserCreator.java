package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class TopTimeParserCreator implements ITimeParserCreator<TopTimeParser> {

    @Override
    public TopTimeParser create() {
        return new TopTimeParser();
    }
}
