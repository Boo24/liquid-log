package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.SdngDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.TopDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.IParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.SdngTimeParserCreator;

import javax.inject.Inject;

@Component
public class SdngParser extends SingleLineHandler implements IParser {

    @Inject
    public SdngParser(SdngDataParser dataParser, SdngTimeParserCreator timeParserFactory){
        super(dataParser, timeParserFactory);

    }

    @Override
    public String getName() {
        return "Sdng";
    }


    @Override
    public ICreator getDataSetCreator() {
        return new SdngDataSetCreator();
    }
}
