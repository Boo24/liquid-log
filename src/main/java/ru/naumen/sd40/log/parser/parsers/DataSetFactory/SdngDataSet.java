package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import ru.naumen.sd40.log.parser.data.ActionDoneStatistics;
import ru.naumen.sd40.log.parser.data.ErrorStatistics;

public class SdngDataSet implements IDataSet {
    private ActionDoneStatistics actionDoneStatistics;
    private ErrorStatistics errorStatistics;

    public SdngDataSet()
    {
        errorStatistics = new ErrorStatistics();
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
}
