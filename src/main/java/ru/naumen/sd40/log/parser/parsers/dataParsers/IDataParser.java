package ru.naumen.sd40.log.parser.parsers.dataParsers;
import ru.naumen.sd40.log.parser.DataSet;


public interface IDataParser {
    int getBufferSize();

    void parseLine(String line, DataSet currentData);
}
