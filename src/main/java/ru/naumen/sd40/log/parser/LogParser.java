package ru.naumen.sd40.log.parser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.GcDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.SdngDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.TopDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.IParser;
import ru.naumen.sd40.log.parser.parsers.dataParsers.BaseDataHandler;
import ru.naumen.sd40.log.parser.parsers.dataParsers.GcParser;
import ru.naumen.sd40.log.parser.parsers.dataParsers.SdngParser;
import ru.naumen.sd40.log.parser.parsers.dataParsers.TopParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TopTimeParser;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by doki on 22.10.16.
 */

@Component
public class LogParser
{
    /**
     * 
     * @param args [0] - sdng.log, [1] - gc.log, [2] - top, [3] - dbName, [4] timezone
     * @throws IOException
     * @throws ParseException
     */

    private static final String TopMode = "Top";
    private static Map<String, BaseDataHandler> modeToParser = new HashMap<>();
    private static Map<String, ICreator> modeToDataSetCreator = new HashMap<>();
    private InfluxDAO influxDAO;

    @Inject
    public LogParser(InfluxDAO influxDAO, Map<String, IParser> parsers){
        this.influxDAO = influxDAO;
        parsers.forEach((k, v) -> {
            modeToParser.put(k, (BaseDataHandler)v);
            modeToDataSetCreator.put(k, v.getDataSetCreator());
        });
    }

    public void parse(AppSettings settings) throws IOException, ParseException
    {
        String mode = settings.parseMode;
        InfluxDBWriter dbWriter = new InfluxDBWriter(this.influxDAO, settings.influxName, settings.trace);

        BaseDataHandler dataHandler = modeToParser.get(mode);
        InfluxDBClient storage = new InfluxDBClient(dbWriter, modeToDataSetCreator.get(mode));
        if(mode == TopMode){
            ((TopTimeParser)dataHandler.getTimeParser()).setDataDate(settings.logFilename);
        }
        dataHandler.setDataBaseClient(storage);

        if (settings.timeZone != null)
            dataHandler.configureTimeParser(settings.timeZone);

        try (BufferedReader br = new BufferedReader(new FileReader(settings.logFilename), dataHandler.getBuffSize()))
        {
            String line;
            while ((line = br.readLine()) != null) {
                dataHandler.handleLine(line);
            }
            storage.flush();
        }
    }
}
