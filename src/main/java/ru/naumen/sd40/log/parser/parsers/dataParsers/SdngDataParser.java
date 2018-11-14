package ru.naumen.sd40.log.parser.parsers.dataParsers;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.ActionDoneParser;
import ru.naumen.sd40.log.parser.DataSet;
import ru.naumen.sd40.log.parser.ErrorParser;

@Component
public class SdngDataParser implements IDataParser {

    @Override
    public int getBufferSize() {
        return 32 * 1024 * 1024;
    }

    @Override
    public void parseLine(String line, DataSet currentData) {
        ActionDoneParser.parseLine(line, currentData);
        if(ErrorParser.checkError(line))
            currentData.getErrorStatistics().changeErrorCount(1);
        else if (ErrorParser.checkFatal(line))
            currentData.getErrorStatistics().changeFatalCount(1);
        else if (ErrorParser.checkWarn(line))
            currentData.getErrorStatistics().changeWarnCount(1);
    }

}
