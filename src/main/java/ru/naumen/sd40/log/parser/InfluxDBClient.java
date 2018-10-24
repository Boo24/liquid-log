package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.InfluxDAO;

public class InfluxDBClient implements IDataBase {

    private InfluxDAO storage = null;
    private InfluxDAO finalStorage;
    private String influxDbAddr;
    private BatchPoints finalPoints;
    private long lastKey = 0;
    private DataSet currentDataSet = new DataSet();

    public InfluxDBClient(String name){
        influxDbAddr = name;
        if (name != null)
        {
            storage = new InfluxDAO(System.getProperty("influx.host"), System.getProperty("influx.user"),
                    System.getProperty("influx.password"));
            storage.init();
            storage.connectToDB(name);
            finalPoints = storage.startBatchPoints(influxDbAddr);
        }
        finalStorage = storage;
    }


    private void Save(long k, DataSet set){
        ActionDoneParser dones = set.getActionsDone();
        dones.calculate();
        ErrorParser errors = set.getErrors();
        if (System.getProperty("NoCsv") == null)
            PrintInCsvFormat(k, dones, errors);
        if (!dones.isNan())
        {
            finalStorage.storeActionsFromLog(finalPoints, influxDbAddr, k, dones, errors);
        }

        GCParser gc = set.getGc();
        if (!gc.isNan())
        {
            finalStorage.storeGc(finalPoints, influxDbAddr, k, gc);
        }

        TopData cpuData = set.cpuData();
        if (!cpuData.isNan())
        {
            finalStorage.storeTop(finalPoints, influxDbAddr, k, cpuData);
        }
    }

    private static void PrintInCsvFormat(Long k, ActionDoneParser dones, ErrorParser errors) {
        System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", k, dones.getCount(),
                dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                dones.getPercent99(), dones.getPercent999(), dones.getMax(), errors.getErrorCount()));

    }
    @Override
    public DataSet get(long key) {
        if (this.lastKey != key)
        {
            Save(this.lastKey, currentDataSet);
            this.lastKey = key;
            currentDataSet = new DataSet();
        }

        return currentDataSet;
    }


}
