package ru.naumen.sd40.log.parser;

import ru.naumen.sd40.log.parser.data.ActionDoneStatistics;
import ru.naumen.sd40.log.parser.data.ErrorStatistics;
import ru.naumen.sd40.log.parser.data.GcStatistics;
import ru.naumen.sd40.log.parser.data.TopStatistics;

/**
 * Created by doki on 22.10.16.
 */
public class DataSet
{

    private GcStatistics gcStatistics;
    private ActionDoneStatistics actionDoneStatistics;
    private ErrorStatistics errorStatistics;
    private TopStatistics cpuStatistics = new TopStatistics();

    public DataSet()
    {
        errorStatistics = new ErrorStatistics();
        gcStatistics = new GcStatistics();
        actionDoneStatistics = new ActionDoneStatistics();
    }


    public ActionDoneStatistics getActionsDoneStatistics()
    {
        return actionDoneStatistics;
    }
    public ErrorStatistics getErrorStatistics()
    {
        return errorStatistics;
    }
    public GcStatistics getGcStatistics(){return gcStatistics;}
    public TopStatistics cpuStatistics()
    {
        return cpuStatistics;
    }
}
