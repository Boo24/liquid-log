package ru.naumen.sd40.log.parser.parsers.dataTypes;

import com.google.common.collect.Lists;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.List;

public class GCDataType implements IDataType {
    public static final String GCTIMES = "gcTimes";
    public static final String AVARAGE_GC_TIME = "avgGcTime";
    public static final String MAX_GC_TIME = "maxGcTime";


    @Override
    public List<String> getProperties() {
        return Lists.newArrayList(Constants.TIME, GCTIMES, AVARAGE_GC_TIME, MAX_GC_TIME);
    }

    @Override
    public String getPathName() {
        return "gc";
    }
}
