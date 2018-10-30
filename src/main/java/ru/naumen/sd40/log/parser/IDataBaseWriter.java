package ru.naumen.sd40.log.parser;

public interface IDataBaseWriter {
    void save(long key, DataSet set);
}
