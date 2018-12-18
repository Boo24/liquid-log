package ru.naumen.sd40.log.parser.parsers.dataParsers;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.RenderDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.IParser;
import ru.naumen.sd40.log.parser.parsers.dataTypes.IDataType;
import ru.naumen.sd40.log.parser.parsers.dataTypes.RenderDataType;
import ru.naumen.sd40.log.parser.parsers.timeParsers.RenderTimeParserCreator;

import java.util.List;

@Component("Render")
public class RenderParser extends SingleLineHandler implements IParser {

    private final static RenderDataType RenderTimeStatistics = new RenderDataType();

    public RenderParser(RenderDataParser dataParser, RenderTimeParserCreator timeParser) {
        super(dataParser, timeParser);
    }

    @Override
    public ICreator getDataSetCreator() {
        return new RenderDataSetCreator();
    }

    @Override
    public List<IDataType> getDataTypes() {
        return Lists.newArrayList(RenderTimeStatistics);
    }
}
