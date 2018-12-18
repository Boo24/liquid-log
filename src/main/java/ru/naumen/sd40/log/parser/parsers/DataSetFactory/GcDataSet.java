package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.influxdb.dto.Point;
import ru.naumen.perfhouse.statdata.Constants;
import ru.naumen.sd40.log.parser.data.GcStatistics;
import ru.naumen.sd40.log.parser.parsers.dataTypes.GCDataType;

import java.util.concurrent.TimeUnit;

public class GcDataSet implements IDataSet {
    private GcStatistics gcStatistics;
    private long key;

    public GcDataSet(long key)
    {
        this.key = key;
        gcStatistics = new GcStatistics();
    }
    public GcStatistics getGcStatistics(){return gcStatistics;}

    @Override
    public Point prepareForStorage() {
        GcStatistics gc = getGcStatistics();
        if(gc.isNan())
            return null;

        return Point.measurement(Constants.MEASUREMENT_NAME)
                .time(key, TimeUnit.MILLISECONDS)
                .addField(GCDataType.GCTIMES, gc.getGcTimes())
                .addField(GCDataType.AVARAGE_GC_TIME, gc.getCalculatedAvg())
                .addField(GCDataType.MAX_GC_TIME, gc.getMaxGcTime())
                .build();
    }

    @Override
    public long getKey() {
        return key;
    }
}
