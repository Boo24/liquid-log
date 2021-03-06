package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.parsers.DataSetFactory.IDataSet;
import ru.naumen.sd40.log.parser.parsers.timeParsers.ITimeParserCreator;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TimeHandleHelper;

import java.text.ParseException;

public class ChunkHandler  extends BaseDataHandler {

    private long currentKey;
    public ChunkHandler(IDataParser dataParser, ITimeParserCreator timeParser){
        super(dataParser, timeParser);
    }


    @Override
    public void handleLine(String line) throws ParseException {
        long time = timeParser.parseTime(line);
        if(time == 0) {
            IDataSet currentObj = dataBase.get(currentKey);
            super.dataParser.parseLine(line, currentObj);
        }
        else
        {
            currentKey = TimeHandleHelper.prepareDate(time);
        }
    }

}
