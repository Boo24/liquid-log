package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.DataSet;

public class ParseLineResult {
    public long Key;
    public DataSet Set;

    public ParseLineResult(long key, DataSet set){
        Key = key;
        Set = set;
    }
}
