package ru.naumen.sd40.log.parser.parsers.dataParsers;


import ru.naumen.sd40.log.parser.DataSet;
import ru.naumen.sd40.log.parser.parsers.timeParsers.ITimeParser;

import java.text.ParseException;
import java.util.HashMap;

public abstract class BaseDataHandler {
    public abstract void handleLine(String line) throws ParseException;

    public int getBuffSize(){
        return dataParser.getBufferSize();
    }

    protected IDataParser dataParser;
    protected ITimeParser timeParser;
    protected HashMap<Long, DataSet> data;

    public BaseDataHandler(IDataParser dataParser, ITimeParser timeParser, HashMap<Long, DataSet> data){
        this.dataParser = dataParser;
        this.timeParser = timeParser;
        this.data = data;
    }

    public void configureTimeParser(String value) {
        timeParser.configureTimeZone(value);
    }
}
