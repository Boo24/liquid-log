package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.influxdb.dto.Point;

public interface IDataSet {

    Point prepareForStorage();

    long getKey();
}
