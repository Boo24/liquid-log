package ru.naumen.sd40.log.parser.parsers.dataTypes;

import com.google.common.collect.Lists;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.List;

public class RenderDataType implements IDataType {

    public static final String MAX = "max";
    public static final String COUNT = "count";
    public static final String MEAN = "mean";
    public static final String MIN = "mean";

    @Override
    public List<String> getProperties() {
        return Lists.newArrayList(Constants.TIME, COUNT, MEAN, MAX);
    }

    @Override
    public String getPathName() {
        return "render";
    }
}
