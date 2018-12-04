package ru.naumen.sd40.log.parser;

import ru.naumen.sd40.log.parser.parsers.DataSetFactory.*;

public class InfluxDBClient implements IDataBaseClient {
    private long lastKey = -1;
    private IDataSet currentDataSet;
    private IDataBaseWriter dbWriter;
    private ICreator creator;

    public InfluxDBClient(IDataBaseWriter dbWriter, ICreator creator){
        this.dbWriter = dbWriter;
        this.creator = creator;
        this.currentDataSet = creator.create();
    }


    @Override
    public IDataSet get(long key) {
        if (this.lastKey != key)
        {
            if(this.lastKey != -1)
               save();
            this.lastKey = key;
            currentDataSet = creator.create();
        }

        return currentDataSet;
    }

    @Override
    public void flush() {
        save();
    }

    private void save(){
        if(currentDataSet instanceof TopDataSet)
            dbWriter.save(this.lastKey, (TopDataSet) currentDataSet);
        else if(currentDataSet instanceof SdngDataSet)
            dbWriter.save(this.lastKey, (SdngDataSet) currentDataSet);
        else
            dbWriter.save(this.lastKey, (GcDataSet) currentDataSet);
    }
}
