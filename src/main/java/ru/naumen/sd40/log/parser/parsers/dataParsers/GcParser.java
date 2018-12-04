package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.timeParsers.GcTimeParserCreator;

import javax.inject.Inject;

@Component
public class GcParser extends SingleLineHandler {

    @Inject
    public GcParser(GcDataParser dataParser, GcTimeParserCreator timeParser) {
        super(dataParser, timeParser);
    }
}
