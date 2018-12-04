package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import ru.naumen.sd40.log.parser.data.GcStatistics;

public class GcDataSet implements IDataSet {
    private GcStatistics gcStatistics;

    public GcDataSet()
    {
        gcStatistics = new GcStatistics();
    }
    public GcStatistics getGcStatistics(){return gcStatistics;}
}
