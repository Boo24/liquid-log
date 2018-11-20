package ru.naumen.sd40.log.parser;

import ru.naumen.sd40.log.parser.parsers.DataSetFactory.GcDataSet;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.SdngDataSet;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.TopDataSet;

public interface IDataBaseWriter {
    void save(long key, TopDataSet set);
    void save(long key, SdngDataSet set);
    void save(long key, GcDataSet set);
}

