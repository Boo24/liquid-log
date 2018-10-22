package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.DataSet;
import ru.naumen.sd40.log.parser.parsers.timeParsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TimeHandleHelper;

import java.text.ParseException;
import java.util.HashMap;

public class ChunkHandler  extends BaseDataHandler {

    private DataSet currentObj;

    public ChunkHandler(IDataParser dataParser, ITimeParser timeParser, HashMap<Long, DataSet> data){
        super(dataParser, timeParser, data);
    }


    @Override
    public void handleLine(String line) throws ParseException {
        long time = timeParser.parseTime(line);
        if(time == 0) {
            super.dataParser.parseLine(line, currentObj);
        }
        else
        {
            long key = TimeHandleHelper.prepareDate(time);
            currentObj = data.computeIfAbsent(key, k -> new DataSet());
        }
    }

}
