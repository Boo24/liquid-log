package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.DataSet;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopDataParser implements IDataParser {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH:mm");
    private Pattern cpuAndMemPattren = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");

    public TopDataParser() throws IllegalArgumentException
    {
        //Supports these masks in file name: YYYYmmdd, YYY-mm-dd i.e. 20161101, 2016-11-01
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public int getBufferSize() {
        return 8192;
    }

    @Override
    public void parseLine(String line, DataSet existingDataSet) {
        if (existingDataSet != null)
        {
            //get la
            Matcher la = Pattern.compile(".*load average:(.*)").matcher(line);
            if (la.find())
            {
                existingDataSet.cpuData().addLa(Double.parseDouble(la.group(1).split(",")[0].trim()));
                return;
            }

            //get cpu and mem
            Matcher cpuAndMemMatcher = cpuAndMemPattren.matcher(line);
            if (cpuAndMemMatcher.find())
            {
                existingDataSet.cpuData().addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
                existingDataSet.cpuData().addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
            }
        }
    }

}
