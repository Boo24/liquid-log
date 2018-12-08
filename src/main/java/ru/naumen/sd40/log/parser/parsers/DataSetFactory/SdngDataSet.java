package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.influxdb.dto.Point;
import ru.naumen.perfhouse.statdata.Constants;
import ru.naumen.sd40.log.parser.data.ActionDoneStatistics;
import ru.naumen.sd40.log.parser.data.ErrorStatistics;

import java.util.concurrent.TimeUnit;

import static ru.naumen.perfhouse.statdata.Constants.PerformedActions.*;
import static ru.naumen.perfhouse.statdata.Constants.PerformedActions.CATALOGS_ACTIONS;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.*;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.ERRORS;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.MAX;

public class SdngDataSet implements IDataSet {
    private ActionDoneStatistics actionDoneStatistics;
    private ErrorStatistics errorStatistics;
    private long key;

    public SdngDataSet(long key)
    {
        this.key = key;
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

    @Override
    public Point prepareForStorage() {
        ActionDoneStatistics dones = getActionsDoneStatistics();
        dones.calculate();
        ErrorStatistics errors = getErrorStatistics();
        if(!dones.isNan()){
            Point.Builder builder = Point.measurement(Constants.MEASUREMENT_NAME).time(key, TimeUnit.MILLISECONDS)
                    .addField(COUNT, dones.getCount())
                    .addField("min", dones.getMin())
                    .addField(MEAN, dones.getMean())
                    .addField(STDDEV, dones.getStddev())
                    .addField(PERCENTILE50, dones.getPercent50())
                    .addField(PERCENTILE95, dones.getPercent95())
                    .addField(PERCENTILE99, dones.getPercent99())
                    .addField(PERCENTILE999, dones.getPercent999())
                    .addField(MAX, dones.getMax())
                    .addField(ERRORS, errors.getErrorCount())
                    .addField(ADD_ACTIONS, dones.getAddObjectActions())
                    .addField(EDIT_ACTIONS, dones.getEditObjectsActions())
                    .addField(LIST_ACTIONS, dones.geListActions())
                    .addField(COMMENT_ACTIONS, dones.getCommentActions())
                    .addField(GET_FORM_ACTIONS, dones.getFormActions())
                    .addField(GET_DT_OBJECT_ACTIONS, dones.getDtObjectActions())
                    .addField(SEARCH_ACTIONS, dones.getSearchActions())
                    .addField(CATALOGS_ACTIONS, dones.getCatalogsAction());
            return builder.build();
        }
        return null;
    }

    @Override
    public long getKey() {
        return key;
    }
}
