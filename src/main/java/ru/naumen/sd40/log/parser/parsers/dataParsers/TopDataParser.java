package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.TopDataSet;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TopDataParser implements IDataParser<TopDataSet> {

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
    public void parseLine(String line, TopDataSet existingDataSet) {
        if (existingDataSet != null)
        {
            //get la
            Matcher la = Pattern.compile(".*load average:(.*)").matcher(line);
            if (la.find())
            {
                existingDataSet.cpuStatistics().addLa(Double.parseDouble(la.group(1).split(",")[0].trim()));
                return;
            }

            //get cpu and mem
            Matcher cpuAndMemMatcher = cpuAndMemPattren.matcher(line);
            if (cpuAndMemMatcher.find())
            {
                existingDataSet.cpuStatistics().addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
                existingDataSet.cpuStatistics().addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
            }
        }
    }

}
