package ru.naumen.sd40.log.parser;

import org.influxdb.dto.Point;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.*;

public class InfluxDBClient implements IDataBaseClient {
    private long lastKey = -1;
    private IDataSet currentDataSet;
    private IDataBaseWriter dbWriter;
    private ICreator creator;

    public InfluxDBClient(IDataBaseWriter dbWriter, ICreator creator){
        this.dbWriter = dbWriter;
        this.creator = creator;
        this.currentDataSet = creator.create(lastKey);
    }


    @Override
    public IDataSet get(long key) {
        if (this.lastKey != key)
        {
            if(this.lastKey != -1)
               save();
            this.lastKey = key;
            currentDataSet = creator.create(key);
        }

        return currentDataSet;
    }

    @Override
    public void flush()
    {
        save();
    }

    private void save(){
        Point point = currentDataSet.prepareForStorage();
        if(point != null)
            dbWriter.save(point);
    }
}
