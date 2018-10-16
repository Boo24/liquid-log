package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.DataSet;
import ru.naumen.sd40.log.parser.parsers.timeParsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TimeHandleHelper;

import java.text.ParseException;
import java.util.HashMap;

public class SingleLineHandler extends BaseDataHandler {

    public SingleLineHandler(IDataParser dataParser, ITimeParser timeParser, HashMap<Long, DataSet> data){
        super(dataParser, timeParser, data);
    }
    @Override
    public void handleLine(String line) throws ParseException {
        long time = timeParser.parseTime(line);
        if (time == 0)
            return;
        long key = TimeHandleHelper.prepareDate(time);
        DataSet obj = data.computeIfAbsent(key, k -> new DataSet());
        dataParser.parseLine(line, obj);
    }
}
