package ru.naumen.sd40.log.parser.parsers.timeParsers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class SdngTimeParserCreator implements ITimeParserCreator<SdngTimeParser> {

    @Override
    @RequestScope
    public SdngTimeParser create() {
        System.out.print("rtgertgherhethy");
        return new SdngTimeParser();
    }
}
