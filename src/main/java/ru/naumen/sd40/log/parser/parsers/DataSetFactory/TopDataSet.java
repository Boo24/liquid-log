package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.influxdb.dto.Point;
import ru.naumen.perfhouse.statdata.Constants;
import ru.naumen.sd40.log.parser.data.TopStatistics;

import java.util.concurrent.TimeUnit;

import static ru.naumen.perfhouse.statdata.Constants.Top.*;
import static ru.naumen.perfhouse.statdata.Constants.Top.MAX_CPU;
import static ru.naumen.perfhouse.statdata.Constants.Top.MAX_MEM;

public class TopDataSet implements IDataSet {
    private TopStatistics cpuStatistics = new TopStatistics();

    public TopStatistics cpuStatistics()
    {
        return cpuStatistics;
    }

    private long key;

    public TopDataSet(long key){
        this.key = key;
    }

    @Override
    public Point prepareForStorage() {
        TopStatistics data = cpuStatistics();
        if(data.isNan())
            return null;
        Point point = Point.measurement(Constants.MEASUREMENT_NAME)
                .time(key, TimeUnit.MILLISECONDS)
                .addField(AVG_LA, data.getAvgLa()).addField(AVG_CPU, data.getAvgCpuUsage())
                .addField(AVG_MEM, data.getAvgMemUsage()).addField(MAX_LA, data.getMaxLa())
                .addField(MAX_CPU, data.getMaxCpu()).addField(MAX_MEM, data.getMaxMem()).build();

        return point;
    }

    @Override
    public long getKey() {
        return key;
    }
}
