package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.data.ActionDoneStatistics;
import ru.naumen.sd40.log.parser.data.ErrorStatistics;
import ru.naumen.sd40.log.parser.data.GcStatistics;
import ru.naumen.sd40.log.parser.data.TopStatistics;

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
    public void save(long k, DataSet set){
        ActionDoneStatistics dones = set.getActionsDoneStatistics();
        dones.calculate();
        if (trace)
            PrintInCsvFormat(k, dones, set.getErrorStatistics());
        if (!dones.isNan())
        {
            storage.storeActionsFromLog(points, influxDbAddr, k, dones, set.getErrorStatistics());
        }

        GcStatistics gc = set.getGcStatistics();
        if (!gc.isNan())
        {
            storage.storeGc(points, influxDbAddr, k, gc);
        }

        TopStatistics cpuData = set.cpuStatistics();
        if (!cpuData.isNan())
        {
            storage.storeTop(points, influxDbAddr, k, cpuData);
        }
    }
}
