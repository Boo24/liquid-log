package ru.naumen.sd40.log.parser;


public class AppSettings {
    public String logFilename;
    public String timeZone;
    public boolean trace;
    public String parseMode;
    public String influxName;

    public AppSettings(String influxName,String parseMode,
                        String filepath,String timezone,
                        String trace){
        logFilename = filepath;
        timeZone = timezone;
        this.parseMode = parseMode;
        this.influxName = influxName;
        if(trace != "no")
            this.trace = true;

    }
}
