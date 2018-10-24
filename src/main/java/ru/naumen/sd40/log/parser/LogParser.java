package ru.naumen.sd40.log.parser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.influxdb.dto.BatchPoints;
import ru.naumen.sd40.log.parser.parsers.dataParsers.*;
import ru.naumen.sd40.log.parser.parsers.timeParsers.*;

/**
 * Created by doki on 22.10.16.
 */
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

    public static void main(String[] args) throws IOException, ParseException
    {
        AppSettings settings = new AppSettings(args);
        InfluxDBClient storage = new InfluxDBClient(settings.influxDbAddr);

        BaseDataHandler dataHandler;
        String mode = System.getProperty("parse.mode", "");
        switch (mode){
            case SdngMode:
                dataHandler = new SingleLineHandler(new SdngDataParser(),new SdngTimeParser(), storage);
                break;
            case GcMode:
                dataHandler = new SingleLineHandler(new GcDataParser(), new GcTimeParser(), storage);
                break;
            case TopMode:
                dataHandler = new ChunkHandler(new TopDataParser(), new TopTimeParser(settings.logFilename), storage);
                break;
            default:
                throw new IllegalArgumentException(
                       "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode);
        }
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
        }
    }
}
