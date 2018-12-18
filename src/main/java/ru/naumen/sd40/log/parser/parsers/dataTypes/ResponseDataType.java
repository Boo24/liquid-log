package ru.naumen.sd40.log.parser.parsers.dataTypes;

import com.google.common.collect.Lists;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.List;

public class ResponseDataType implements IDataType {
    public static final String PERCENTILE50 = "percent50";
    public static final String PERCENTILE95 = "percent95";
    public static final String PERCENTILE99 = "percent99";
    public static final String PERCENTILE999 = "percent999";
    public static final String MAX = "max";
    public static final String COUNT = "count";
    public static final String ERRORS = "errors";
    public static final String MEAN = "mean";
    public static final String STDDEV = "stddev";

    @Override
    public List<String> getProperties() {
        return Lists.newArrayList(Constants.TIME, COUNT, ERRORS, MEAN, STDDEV, PERCENTILE50, PERCENTILE95, PERCENTILE99,
                PERCENTILE999, MAX);
    }

    @Override
    public String getPathName() {
        return "response";
    }
}
