package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.InfluxDAO;

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
    private static void PrintInCsvFormat(Long k, ActionDoneParser dones, ErrorParser errors) {
        System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", k, dones.getCount(),
                dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                dones.getPercent99(), dones.getPercent999(), dones.getMax(), errors.getErrorCount()));

    }
    @Override
    public void save(long k, DataSet set){
        ActionDoneParser dones = set.getActionsDone();
        dones.calculate();
        ErrorParser errors = set.getErrors();
        if (trace)
            PrintInCsvFormat(k, dones, errors);
        if (!dones.isNan())
        {
            storage.storeActionsFromLog(points, influxDbAddr, k, dones, errors);
        }

        GCParser gc = set.getGc();
        if (!gc.isNan())
        {
            storage.storeGc(points, influxDbAddr, k, gc);
        }

        TopData cpuData = set.cpuData();
        if (!cpuData.isNan())
        {
            storage.storeTop(points, influxDbAddr, k, cpuData);
        }
    }
}
