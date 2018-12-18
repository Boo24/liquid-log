package ru.naumen.sd40.log.parser;

import org.influxdb.dto.Point;

public interface IDataBaseWriter {
    void save(Point point);
}

