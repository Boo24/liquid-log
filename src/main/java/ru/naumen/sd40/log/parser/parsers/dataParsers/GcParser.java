package ru.naumen.sd40.log.parser.parsers.dataParsers;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.GcDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;
import ru.naumen.sd40.log.parser.parsers.IParser;
import ru.naumen.sd40.log.parser.parsers.dataTypes.GCDataType;
import ru.naumen.sd40.log.parser.parsers.dataTypes.IDataType;
import ru.naumen.sd40.log.parser.parsers.timeParsers.GcTimeParserCreator;

import javax.inject.Inject;
import java.util.List;

@Component("Gc")
public class GcParser extends SingleLineHandler implements IParser {
    private static final GCDataType DATA_TYPE = new GCDataType();

    @Inject
    public GcParser(GcDataParser dataParser, GcTimeParserCreator timeParser) {
        super(dataParser, timeParser);
    }

    @Override
    public ICreator getDataSetCreator() {
        return new GcDataSetCreator();
    }

    @Override
    public List<IDataType> getDataTypes() {
        return Lists.newArrayList(DATA_TYPE);
    }
}
