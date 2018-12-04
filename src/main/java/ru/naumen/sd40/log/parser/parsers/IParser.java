package ru.naumen.sd40.log.parser.parsers;

import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;

public interface IParser {
    String getName();

    ICreator getDataSetCreator();
}
