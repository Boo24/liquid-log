package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.naumen.sd40.log.parser.parsers.timeParsers.SdngTimeParserCreator;

import javax.inject.Inject;

@Component
@RequestScope
public class SdngParser extends SingleLineHandler{

    @Inject
    public SdngParser(SdngDataParser dataParser, SdngTimeParserCreator timeParserFactory){
        super(dataParser, timeParserFactory);

    }
}
