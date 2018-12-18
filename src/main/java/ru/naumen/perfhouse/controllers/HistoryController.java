package ru.naumen.perfhouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.perfhouse.statdata.StatData;
import ru.naumen.perfhouse.statdata.StatDataService;
import ru.naumen.sd40.log.parser.parsers.IParser;
import ru.naumen.sd40.log.parser.parsers.dataTypes.IDataType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by doki on 23.10.16.
 */
@Controller
public class HistoryController
{
    private StatDataService service;
    private Map<String, IDataType> dataTypes = new HashMap<>();

    private IDataType defaultDataType = null;

    @Autowired
    public HistoryController(StatDataService service, List<IParser> parsers){
        this.service = service;

        for(IParser parser: parsers)
        for(IDataType dataType: parser.getDataTypes())
            dataTypes.put(dataType.getPathName(), dataType);

        if(dataTypes.size() != 0)
            defaultDataType = dataTypes.entrySet().iterator().next().getValue();
    }


    private static final String NO_HISTORY_VIEW = "no_history";

    @RequestMapping(path = "/history/{client}/{year}/{month}/{day}")
    public ModelAndView indexByDay(@PathVariable("client") String client,
            @PathVariable(name = "year", required = false) int year,
            @PathVariable(name = "month", required = false) int month,
            @PathVariable(name = "day", required = false) int day) throws ParseException
    {
        return getDataAndViewByDate(client, defaultDataType, year, month, day, defaultDataType.getPathName());
    }

    @RequestMapping(path = "/history/{client}/{dataType}/{year}/{month}/{day}")
    public ModelAndView actionsByDay(@PathVariable("client") String client,
                                     @PathVariable("dataType") String dataType,
            @PathVariable(name = "year", required = false) int year,
            @PathVariable(name = "month", required = false) int month,
            @PathVariable(name = "day", required = false) int day) throws ParseException
    {
        IDataType type = dataTypes.getOrDefault(dataType, defaultDataType);
        return getDataAndViewByDate(client, type, year, month, day, type.getPathName());
    }

    @RequestMapping(path = "/history/{client}/{year}/{month}")
    public ModelAndView indexByMonth(@PathVariable("client") String client,
            @PathVariable(name = "year", required = false) int year,
            @PathVariable(name = "month", required = false) int month) throws ParseException
    {
        return getDataAndViewByDate(client, defaultDataType, year, month, 0, defaultDataType.getPathName(), true);
    }

    @RequestMapping(path = "/history/{client}/{dataType}/{year}/{month}")
    public ModelAndView actionsByMonth(@PathVariable("client") String client,
                                       @PathVariable("dataType") String dataType,
            @PathVariable(name = "year", required = false) int year,
            @PathVariable(name = "month", required = false) int month) throws ParseException
    {
        IDataType type = dataTypes.getOrDefault(dataType, defaultDataType);
        return getDataAndViewByDate(client, type, year, month, 0, type.getPathName(), true);
    }

    @RequestMapping(path = "/history/{client}")
    public ModelAndView indexLast864(@PathVariable("client") String client,
            @RequestParam(name = "count", defaultValue = "864") int count) throws ParseException
    {
        ru.naumen.perfhouse.statdata.StatData d = service.getData(client, defaultDataType, count);

        if (d == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }

        Map<String, Object> model = new HashMap<>(d.asModel());
        model.put("client", client);
        addTypesToModel(model);
        return new ModelAndView(defaultDataType.getPathName(), model, HttpStatus.OK);
    }

    @RequestMapping(path = "/history/{client}/{dataType}")
    public ModelAndView gcLast864(@PathVariable("client") String client,
                                  @PathVariable("dataType") String dataType,
            @RequestParam(name = "count", defaultValue = "864") int count) throws ParseException
    {
        IDataType type = dataTypes.getOrDefault(dataType, defaultDataType);
        return getDataAndView(client, type, count, type.getPathName());

    }

    private ModelAndView getDataAndView(String client, IDataType dataType, int count, String viewName)
            throws ParseException
    {
        ru.naumen.perfhouse.statdata.StatData data = service.getData(client, dataType, count);
        if (data == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }
        Map<String, Object> model = new HashMap<>(data.asModel());
        model.put("client", client);
        addTypesToModel(model);
        return new ModelAndView(viewName, model, HttpStatus.OK);
    }

    private ModelAndView getDataAndViewByDate(String client, IDataType type, int year, int month, int day,
            String viewName) throws ParseException
    {
        return getDataAndViewByDate(client, type, year, month, day, viewName, false);
    }

    private ModelAndView getDataAndViewByDate(String client, IDataType type, int year, int month, int day,
            String viewName, boolean compress) throws ParseException
    {
        ru.naumen.perfhouse.statdata.StatData dataDate = service.getDataDate(client, type, year, month, day);
        if (dataDate == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }

        dataDate = compress ? service.compress(dataDate, 3 * 60 * 24 / 5) : dataDate;
        Map<String, Object> model = new HashMap<>(dataDate.asModel());
        model.put("client", client);
        model.put("year", year);
        model.put("month", month);
        model.put("day", day);
        addTypesToModel(model);
        return new ModelAndView(viewName, model, HttpStatus.OK);
    }

    private ModelAndView getDataAndViewCustom(String client, IDataType dataType, String from, String to, int maxResults,
            String viewName) throws ParseException
    {
        StatData data = service.getDataCustom(client, dataType, from, to);
        if (data == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }
        data = service.compress(data, maxResults);
        Map<String, Object> model = new HashMap<>(data.asModel());
        model.put("client", client);
        model.put("custom", true);
        model.put("from", from);
        model.put("to", to);
        model.put("maxResults", maxResults);
        addTypesToModel(model);
        return new ModelAndView(viewName, model, HttpStatus.OK);
    }

    @RequestMapping(path = "/history/{client}/custom")
    public ModelAndView customIndex(@PathVariable("client") String client, @RequestParam("from") String from,
            @RequestParam("to") String to, @RequestParam("maxResults") int maxResults) throws ParseException
    {
        return getDataAndViewCustom(client, defaultDataType, from, to, maxResults, defaultDataType.getPathName());
    }

    @RequestMapping(path = "/history/{client}/custom/{dataType}")
    public ModelAndView customActions(@PathVariable("client") String client,
                                      @PathVariable("dataType") String dataType,
                                      @RequestParam("from") String from,
            @RequestParam("to") String to, @RequestParam("maxResults") int count) throws ParseException
    {
        IDataType type = dataTypes.getOrDefault(dataType, defaultDataType);
        return getDataAndViewCustom(client, type, from, to, count, type.getPathName());
    }

    private void addTypesToModel(Map<String, Object> model){
        model.put("types", dataTypes.keySet());
    }
}
