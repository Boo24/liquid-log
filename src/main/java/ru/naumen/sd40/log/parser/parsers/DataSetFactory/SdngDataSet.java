package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.influxdb.dto.Point;
import ru.naumen.perfhouse.statdata.Constants;
import ru.naumen.sd40.log.parser.data.ActionDoneStatistics;
import ru.naumen.sd40.log.parser.data.ErrorStatistics;
import ru.naumen.sd40.log.parser.parsers.dataTypes.ActionsDataType;
import ru.naumen.sd40.log.parser.parsers.dataTypes.ResponseDataType;

import java.util.concurrent.TimeUnit;

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
                    .addField(ResponseDataType.COUNT, dones.getCount())
                    .addField("min", dones.getMin())
                    .addField(ResponseDataType.MEAN, dones.getMean())
                    .addField(ResponseDataType.STDDEV, dones.getStddev())
                    .addField(ResponseDataType.PERCENTILE50, dones.getPercent50())
                    .addField(ResponseDataType.PERCENTILE95, dones.getPercent95())
                    .addField(ResponseDataType.PERCENTILE99, dones.getPercent99())
                    .addField(ResponseDataType.PERCENTILE999, dones.getPercent999())
                    .addField(ResponseDataType.MAX, dones.getMax())
                    .addField(ResponseDataType.ERRORS, errors.getErrorCount())
                    .addField(ActionsDataType.ADD_ACTIONS, dones.getAddObjectActions())
                    .addField(ActionsDataType.EDIT_ACTIONS, dones.getEditObjectsActions())
                    .addField(ActionsDataType.LIST_ACTIONS, dones.geListActions())
                    .addField(ActionsDataType.COMMENT_ACTIONS, dones.getCommentActions())
                    .addField(ActionsDataType.GET_FORM_ACTIONS, dones.getFormActions())
                    .addField(ActionsDataType.GET_DT_OBJECT_ACTIONS, dones.getDtObjectActions())
                    .addField(ActionsDataType.SEARCH_ACTIONS, dones.getSearchActions())
                    .addField(ActionsDataType.CATALOGS_ACTIONS, dones.getCatalogsAction());
            return builder.build();
        }
        return null;
    }

    @Override
    public long getKey() {
        return key;
    }
}
