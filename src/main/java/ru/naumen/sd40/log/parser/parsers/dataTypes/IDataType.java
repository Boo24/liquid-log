package ru.naumen.sd40.log.parser.parsers.dataTypes;

import java.util.List;

public interface IDataType {
    List<String> getProperties();
    String getPathName();
}
