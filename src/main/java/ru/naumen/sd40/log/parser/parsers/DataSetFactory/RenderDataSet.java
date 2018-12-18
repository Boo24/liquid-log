package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.influxdb.dto.Point;
import ru.naumen.perfhouse.statdata.Constants;
import ru.naumen.sd40.log.parser.data.RenderTimeStatistics;
import ru.naumen.sd40.log.parser.parsers.dataTypes.RenderDataType;

import java.util.concurrent.TimeUnit;

public class RenderDataSet implements IDataSet {

    private RenderTimeStatistics renderTimeStatistics;
    private long key;

    public RenderDataSet(long key)
    {
        this.key = key;
        renderTimeStatistics = new RenderTimeStatistics();
    }
    public RenderTimeStatistics getRenderTimeStatistics(){return renderTimeStatistics;}


    @Override
    public Point prepareForStorage() {

        RenderTimeStatistics rtStatistics = getRenderTimeStatistics();

        rtStatistics.calculate();

        return Point.measurement(Constants.MEASUREMENT_NAME)
                .time(key, TimeUnit.MILLISECONDS)
                .addField(RenderDataType.COUNT, rtStatistics.getCount())
                .addField(RenderDataType.MAX, rtStatistics.getMaxRenderTime())
                .addField(RenderDataType.MIN, rtStatistics.getMinRenderTime())
                .addField(RenderDataType.MEAN, rtStatistics.getCalculatedAvg())
                .build();
    }

    @Override
    public long getKey() {
        return key;
    }
}
