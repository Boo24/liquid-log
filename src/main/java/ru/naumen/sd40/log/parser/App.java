package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.influxdb.dto.BatchPoints;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.parsers.dataParsers.GcDataParser;
import ru.naumen.sd40.log.parser.parsers.dataParsers.IDataParser;
import ru.naumen.sd40.log.parser.parsers.dataParsers.SdngDataParser;
import ru.naumen.sd40.log.parser.parsers.dataParsers.TopDataParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.*;

/**
 * Created by doki on 22.10.16.
 */
public class App
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
    public static void main(String[] args) throws IOException, ParseException
    {
        String influxDb = null;

        if (args.length > 1)
        {
            influxDb = args[1];
            influxDb = influxDb.replaceAll("-", "_");
        }

        InfluxDAO storage = null;
        if (influxDb != null)
        {
            storage = new InfluxDAO(System.getProperty("influx.host"), System.getProperty("influx.user"),
                    System.getProperty("influx.password"));
            storage.init();
            storage.connectToDB(influxDb);
        }
        InfluxDAO finalStorage = storage;
        String finalInfluxDb = influxDb;
        BatchPoints points = null;

        if (storage != null)
        {
            points = storage.startBatchPoints(influxDb);
        }

        String log = args[0];

        HashMap<Long, DataSet> data = new HashMap<>();

        ITimeParser timeParser;
        IDataParser dataParser ;
        String mode = System.getProperty("parse.mode", "");
        switch (mode){
            case SdngMode:
                timeParser = new SdngTimeParser();
                dataParser = new SdngDataParser();
                break;
            case GcMode:
                timeParser = new GcTimeParser();
                dataParser = new GcDataParser();
                break;
            case TopMode:
                timeParser = new TopTimeParser(log);
                dataParser = new TopDataParser();
                break;
            default:
                throw new IllegalArgumentException(
                       "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode);
        }
        if (args.length > 2)
            timeParser.configureTimeZone(args[2]);

        DataSet obj = null;
        try (BufferedReader br = new BufferedReader(new FileReader(log), dataParser.getBufferSize()))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                long time = timeParser.parseTime(line);

                if (time == 0 && mode != TopMode && obj != null)
                    continue;
                long key = TimeHandleHelper.prepareDate(time);
                obj = data.computeIfAbsent(key, k -> new DataSet());
                dataParser.parseLine(line, obj);
            }
        }

        if (System.getProperty("NoCsv") == null)
        {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
        BatchPoints finalPoints = points;
        data.forEach((k, set) ->
        {
            ActionDoneParser dones = set.getActionsDone();
            dones.calculate();
            ErrorParser erros = set.getErrors();
            if (System.getProperty("NoCsv") == null)
            {
                System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", k, dones.getCount(),
                        dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                        dones.getPercent99(), dones.getPercent999(), dones.getMax(), erros.getErrorCount()));
            }
            if (!dones.isNan())
            {
                finalStorage.storeActionsFromLog(finalPoints, finalInfluxDb, k, dones, erros);
            }

            GCParser gc = set.getGc();
            if (!gc.isNan())
            {
                finalStorage.storeGc(finalPoints, finalInfluxDb, k, gc);
            }

            TopData cpuData = set.cpuData();
            if (!cpuData.isNan())
            {
                finalStorage.storeTop(finalPoints, finalInfluxDb, k, cpuData);
            }
        });
        storage.writeBatch(points);
    }
}
