package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.DataSet;
import ru.naumen.sd40.log.parser.IDataBase;
import ru.naumen.sd40.log.parser.parsers.timeParsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TimeHandleHelper;

import java.text.ParseException;

public class ChunkHandler  extends BaseDataHandler {

    private long currentKey;
    public ChunkHandler(IDataParser dataParser, ITimeParser timeParser, IDataBase db){
        super(dataParser, timeParser, db);
    }


    @Override
    public void handleLine(String line) throws ParseException {
        long time = timeParser.parseTime(line);
        if(time == 0) {
            DataSet currentObj = dataBase.get(currentKey);
            super.dataParser.parseLine(line, currentObj);
        }
        else
        {
            currentKey = TimeHandleHelper.prepareDate(time);
        }
    }

}
