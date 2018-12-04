package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.stereotype.Component;

@Component
public class SdngTimeParserCreator implements ITimeParserCreator<SdngTimeParser> {

    @Override
    public SdngTimeParser create() {
        return new SdngTimeParser();
    }
}
