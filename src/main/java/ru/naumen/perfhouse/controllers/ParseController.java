package ru.naumen.perfhouse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.naumen.sd40.log.parser.AppSettings;
import ru.naumen.sd40.log.parser.LogParser;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;

@Controller
public class ParseController {

    private LogParser logParser;
    @Inject
    public ParseController(LogParser logParser)
    {
        this.logParser = logParser;
    }
    @RequestMapping(path = "parse", method = RequestMethod.POST)
    public void parseLogs(@RequestParam("influx") String influxName, @RequestParam("mode") String parseMode,
                         @RequestParam("filepath") String filepath,@RequestParam("timezone") String timezone,
                         @RequestParam("trace") String trace) throws IOException, ParseException

    {

        AppSettings settings = new AppSettings(influxName, parseMode, filepath, timezone, trace);
        logParser.parse(settings);
    }
}
