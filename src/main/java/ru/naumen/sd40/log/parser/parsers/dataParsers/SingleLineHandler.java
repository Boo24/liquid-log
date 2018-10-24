package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.DataSet;
import ru.naumen.sd40.log.parser.IDataBase;
import ru.naumen.sd40.log.parser.parsers.timeParsers.ITimeParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TimeHandleHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class SingleLineHandler extends BaseDataHandler {

    public SingleLineHandler(IDataParser dataParser, ITimeParser timeParser, IDataBase db){
        super(dataParser, timeParser, db);
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
