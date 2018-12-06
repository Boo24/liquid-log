package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.GcDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;
import ru.naumen.sd40.log.parser.parsers.IParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.GcTimeParserCreator;

import javax.inject.Inject;

@Component("Gc")
public class GcParser extends SingleLineHandler implements IParser {

    @Inject
    public GcParser(GcDataParser dataParser, GcTimeParserCreator timeParser) {
        super(dataParser, timeParser);
    }

    @Override
    public ICreator getDataSetCreator() {
        return new GcDataSetCreator();
    }
}
