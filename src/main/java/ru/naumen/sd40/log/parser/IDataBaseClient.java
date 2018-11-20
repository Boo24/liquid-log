package ru.naumen.sd40.log.parser;

import ru.naumen.sd40.log.parser.parsers.DataSetFactory.IDataSet;

public interface IDataBaseClient {
    IDataSet get(long key);

    void flush();
}
