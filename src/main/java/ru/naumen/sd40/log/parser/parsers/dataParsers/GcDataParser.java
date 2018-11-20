package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.GCParser;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.GcDataSet;

@Component
public class GcDataParser implements IDataParser<GcDataSet> {

    @Override
    public int getBufferSize() {
        return 8192;
    }

    @Override
    public void parseLine(String line, GcDataSet currentData) {
        currentData.getGcStatistics().addTime(GCParser.parseLine(line));
    }

}
