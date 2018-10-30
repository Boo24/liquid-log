package ru.naumen.sd40.log.parser;

public class AppSettings {
    public String logFilename;
    public String influxDbAddr;
    public String timeZone;

    public AppSettings(String[] args){
        logFilename = args[0];
        if (args.length > 2)
            timeZone = args[2];
        if (args.length > 1)
        {
            influxDbAddr = args[1];
            influxDbAddr = influxDbAddr.replaceAll("-", "_");
        }
    }
}
