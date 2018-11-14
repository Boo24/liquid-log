package ru.naumen.sd40.log.parser;

public class InfluxDBClient implements IDataBaseClient {
    private long lastKey = -1;
    private DataSet currentDataSet = new DataSet();
    private IDataBaseWriter dbWriter;

    public InfluxDBClient(IDataBaseWriter dbWriter){
        this.dbWriter = dbWriter;
    }


    @Override
    public DataSet get(long key) {
        if (this.lastKey != key)
        {
            if(this.lastKey != -1)
                dbWriter.save(this.lastKey, currentDataSet);
            this.lastKey = key;
            currentDataSet = new DataSet();
        }

        return currentDataSet;
    }

    @Override
    public void flush() {
        dbWriter.save(this.lastKey, currentDataSet);
    }


}
