package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.influxdb.dto.Point;
import ru.naumen.perfhouse.statdata.Constants;
import ru.naumen.sd40.log.parser.data.GcStatistics;

import java.util.concurrent.TimeUnit;

import static ru.naumen.perfhouse.statdata.Constants.GarbageCollection.AVARAGE_GC_TIME;
import static ru.naumen.perfhouse.statdata.Constants.GarbageCollection.GCTIMES;
import static ru.naumen.perfhouse.statdata.Constants.GarbageCollection.MAX_GC_TIME;

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
                .addField(GCTIMES, gc.getGcTimes())
                .addField(AVARAGE_GC_TIME, gc.getCalculatedAvg())
                .addField(MAX_GC_TIME, gc.getMaxGcTime())
                .build();
    }

    @Override
    public long getKey() {
        return key;
    }
}
