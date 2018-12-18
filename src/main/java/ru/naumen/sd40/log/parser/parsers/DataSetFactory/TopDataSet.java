package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.influxdb.dto.Point;
import ru.naumen.perfhouse.statdata.Constants;
import ru.naumen.sd40.log.parser.data.TopStatistics;
import ru.naumen.sd40.log.parser.parsers.dataTypes.TOPDataType;

import java.util.concurrent.TimeUnit;

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
                .addField(TOPDataType.AVG_LA, data.getAvgLa()).addField(TOPDataType.AVG_CPU, data.getAvgCpuUsage())
                .addField(TOPDataType.AVG_MEM, data.getAvgMemUsage()).addField(TOPDataType.MAX_LA, data.getMaxLa())
                .addField(TOPDataType.MAX_CPU, data.getMaxCpu()).addField(TOPDataType.MAX_MEM, data.getMaxMem()).build();

        return point;
    }

    @Override
    public long getKey() {
        return key;
    }
}
