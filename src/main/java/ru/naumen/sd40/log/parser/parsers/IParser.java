package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;
import ru.naumen.sd40.log.parser.parsers.dataTypes.IDataType;

import java.util.List;

public interface IParser {
    ICreator getDataSetCreator();
    List<IDataType> getDataTypes();
}
