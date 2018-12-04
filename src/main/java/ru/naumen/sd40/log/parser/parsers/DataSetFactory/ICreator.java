package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

public interface ICreator<DS extends IDataSet> {
    DS create();
}
