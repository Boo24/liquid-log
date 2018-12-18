package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.stereotype.Component;

@Component
public class RenderTimeParserCreator implements ITimeParserCreator<RenderTimeParser> {

    @Override
    public RenderTimeParser create() {
        return new RenderTimeParser();
    }
}
