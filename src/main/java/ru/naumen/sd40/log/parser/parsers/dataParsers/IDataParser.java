package ru.naumen.sd40.log.parser.parsers.dataParsers;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.IDataSet;


public interface IDataParser<DS extends IDataSet> {
    int getBufferSize();

    void parseLine(String line, DS currentData);
}
