package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.DataSet;
import ru.naumen.sd40.log.parser.IDataBaseClient;
import ru.naumen.sd40.log.parser.parsers.timeParsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TimeHandleHelper;

import java.text.ParseException;

public class SingleLineHandler extends BaseDataHandler {

    public SingleLineHandler(IDataParser dataParser, ITimeParser timeParser){
        super(dataParser, timeParser);
    }
    @Override
    public void handleLine(String line) throws ParseException {
        long time = timeParser.parseTime(line);
        if (time == 0)
            return;
        long key = TimeHandleHelper.prepareDate(time);
        DataSet obj = this.dataBase.get(key);
        dataParser.parseLine(line, obj);

    }
}
