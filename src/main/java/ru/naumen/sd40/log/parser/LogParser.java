package ru.naumen.sd40.log.parser;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.parsers.dataParsers.BaseDataHandler;
import ru.naumen.sd40.log.parser.parsers.dataParsers.GcParser;
import ru.naumen.sd40.log.parser.parsers.dataParsers.SdngParser;
import ru.naumen.sd40.log.parser.parsers.dataParsers.TopParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TopTimeParser;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

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
    private static final String SdngMode = "sdng";
    private static  final String GcMode = "gc";
    private static final String TopMode = "top";
    private static final HashMap<String, BaseDataHandler> modeToParser = new HashMap<>();
    private InfluxDAO influxDAO;

    @Inject
    public LogParser(InfluxDAO influxDAO, SdngParser sdngParser, GcParser gcParser, TopParser topParser){
        this.influxDAO = influxDAO;
        modeToParser.put(SdngMode, sdngParser);
        modeToParser.put(GcMode, gcParser);
        modeToParser.put(TopMode, topParser);

    }

    public void parse(AppSettings settings) throws IOException, ParseException
    {
        InfluxDBWriter dbWriter = new InfluxDBWriter(this.influxDAO, settings.influxName, settings.trace);
        InfluxDBClient storage = new InfluxDBClient(dbWriter);
        String mode = settings.parseMode;
        BaseDataHandler dataHandler = modeToParser.get(mode);
        if(mode == TopMode){
            ((TopTimeParser)dataHandler.getTimeParser()).setDataDate(settings.logFilename);
        }
        dataHandler.setDataBaseClient(storage);

        if (settings.timeZone != null)
            dataHandler.configureTimeParser(settings.timeZone);

        if (System.getProperty("NoCsv") == null)
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");

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
