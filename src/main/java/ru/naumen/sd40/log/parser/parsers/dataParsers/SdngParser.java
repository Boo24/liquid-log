package ru.naumen.sd40.log.parser.parsers.dataParsers;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.SdngDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.TopDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.IParser;
import ru.naumen.sd40.log.parser.parsers.dataTypes.ActionsDataType;
import ru.naumen.sd40.log.parser.parsers.dataTypes.IDataType;
import ru.naumen.sd40.log.parser.parsers.dataTypes.ResponseDataType;
import ru.naumen.sd40.log.parser.parsers.timeParsers.SdngTimeParserCreator;

import javax.inject.Inject;
import java.util.List;

@Component("Sdng")
public class SdngParser extends SingleLineHandler implements IParser {
    private final static ActionsDataType ACTIONS_DATA_TYPE = new ActionsDataType();
    private final static ResponseDataType RESPONSE_DATA_TYPE = new ResponseDataType();
    @Inject
    public SdngParser(SdngDataParser dataParser, SdngTimeParserCreator timeParserFactory){
        super(dataParser, timeParserFactory);

    }

    @Override
    public ICreator getDataSetCreator() {
        return new SdngDataSetCreator();
    }

    @Override
    public List<IDataType> getDataTypes() {
        return Lists.newArrayList(ACTIONS_DATA_TYPE, RESPONSE_DATA_TYPE);
    }
}
