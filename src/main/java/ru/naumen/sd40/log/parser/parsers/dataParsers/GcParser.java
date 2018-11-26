package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.naumen.sd40.log.parser.parsers.timeParsers.GcTimeParserCreator;

import javax.inject.Inject;

@Component
@RequestScope
public class GcParser extends SingleLineHandler {

    @Inject
    public GcParser(GcDataParser dataParser, GcTimeParserCreator timeParseractory) {
        super(dataParser, timeParseractory);
    }
}
