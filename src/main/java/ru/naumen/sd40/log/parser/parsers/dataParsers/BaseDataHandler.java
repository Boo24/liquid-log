package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.IDataBaseClient;
import ru.naumen.sd40.log.parser.parsers.timeParsers.ITimeParser;

import java.text.ParseException;

public abstract class BaseDataHandler {
    public abstract void handleLine(String line) throws ParseException;

    public int getBuffSize(){
        return dataParser.getBufferSize();
    }

    protected IDataParser dataParser;
    protected ITimeParser timeParser;
    protected IDataBaseClient dataBase;

    public BaseDataHandler(IDataParser dataParser, ITimeParser timeParser){
        this.dataParser = dataParser;
        this.timeParser = timeParser;
    }

    public void configureTimeParser(String value) {
        timeParser.configureTimeZone(value);
    }

    public ITimeParser getTimeParser() {return timeParser;}

    public void setDataBaseClient(IDataBaseClient db){
        this.dataBase = db;
    }
}
