package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.data.ActionDoneStatistics;
import ru.naumen.sd40.log.parser.data.ErrorStatistics;
import ru.naumen.sd40.log.parser.data.GcStatistics;
import ru.naumen.sd40.log.parser.data.TopStatistics;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.GcDataSet;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.SdngDataSet;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.TopDataSet;

public class InfluxDBWriter implements IDataBaseWriter{

    private InfluxDAO storage = null;
    private String influxDbAddr;
    private BatchPoints points;
    private boolean trace;

    public InfluxDBWriter(InfluxDAO influxDAO, String name, boolean trace){
        influxDbAddr = name;
        this.trace = trace;
        if (name != null)
        {
            storage =  influxDAO;
            storage.init();
            storage.connectToDB(name);
            points = storage.startBatchPoints(influxDbAddr);
        }
    }
    private static void PrintInCsvFormat(Long k, ActionDoneStatistics dones, ErrorStatistics errorStat) {
        System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", k, dones.getCount(),
                dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                dones.getPercent99(), dones.getPercent999(), dones.getMax(), errorStat.getErrorCount()));

    }


    @Override
    public void save(Point point) {
        storage.store(points, influxDbAddr, point);
    }
//
//    @Override
//    public void save(long key, TopDataSet set) {
//        TopStatistics cpuData = set.cpuStatistics();
//        if (!cpuData.isNan())
//        {
//            storage.storeTop(points, influxDbAddr, key, cpuData);
//        }
//    }
//
//    @Override
//    public void save(long key, SdngDataSet set) {
//        ActionDoneStatistics dones = set.getActionsDoneStatistics();
//        dones.calculate();
//        if (trace)
//            PrintInCsvFormat(key, dones, set.getErrorStatistics());
//        if (!dones.isNan())
//        {
//            storage.storeActionsFromLog(points, influxDbAddr, key, dones, set.getErrorStatistics());
//        }
//    }
//
//    @Override
//    public void save(long key, GcDataSet set) {
//        GcStatistics gc = set.getGcStatistics();
//        if (!gc.isNan())
//        {
//            storage.storeGc(points, influxDbAddr, key, gc);
//        }
//    }
}
