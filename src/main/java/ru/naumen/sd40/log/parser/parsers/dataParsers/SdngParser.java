package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.timeParsers.SdngTimeParser;

import javax.inject.Inject;

@Component
public class SdngParser extends SingleLineHandler{

    @Inject
    public SdngParser(SdngDataParser dataParser, SdngTimeParser timeParser){
        super(dataParser, timeParser);

    }
}
