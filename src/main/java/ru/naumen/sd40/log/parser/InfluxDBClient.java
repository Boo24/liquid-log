package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.InfluxDAO;

public class InfluxDBClient implements IDataBaseClient {


    private long lastKey = 0;
    private DataSet currentDataSet = new DataSet();
    private IDataBaseWriter dbWriter;

    public InfluxDBClient(IDataBaseWriter dbWriter){
        this.dbWriter = dbWriter;
    }


    @Override
    public DataSet get(long key) {
        if (this.lastKey != key)
        {
            dbWriter.save(this.lastKey, currentDataSet);
            this.lastKey = key;
            currentDataSet = new DataSet();
        }

        return currentDataSet;
    }


}
