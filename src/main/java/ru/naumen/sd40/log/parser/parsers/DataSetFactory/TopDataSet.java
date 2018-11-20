package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import ru.naumen.sd40.log.parser.data.TopStatistics;

public class TopDataSet implements IDataSet {
    private TopStatistics cpuStatistics = new TopStatistics();

    public TopStatistics cpuStatistics()
    {
        return cpuStatistics;
    }
}
