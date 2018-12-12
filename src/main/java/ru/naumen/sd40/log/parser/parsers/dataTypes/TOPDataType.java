package ru.naumen.sd40.log.parser.parsers.dataTypes;

import com.google.common.collect.Lists;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.List;

public class TOPDataType implements IDataType {
    public static final String AVG_LA = "avgLa";
    public static final String AVG_CPU = "avgCpu";
    public static final String AVG_MEM = "avgMem";
    public static final String MAX_LA = "maxLa";
    public static final String MAX_CPU = "maxCpu";
    public static final String MAX_MEM = "maxMem";

    @Override
    public List<String> getProperties() {
        return Lists.newArrayList(Constants.TIME, AVG_LA, AVG_CPU, AVG_MEM, MAX_LA, MAX_CPU, MAX_MEM);
    }

    @Override
    public String getPathName() {
        return "top";
    }
}
