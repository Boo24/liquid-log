package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.DataSet;
import ru.naumen.sd40.log.parser.GCParser;

@Component
public class GcDataParser implements IDataParser {

    @Override
    public int getBufferSize() {
        return 8192;
    }

    @Override
    public void parseLine(String line, DataSet currentData) {
        currentData.getGcStatistics().addTime(GCParser.parseLine(line));
    }

}
