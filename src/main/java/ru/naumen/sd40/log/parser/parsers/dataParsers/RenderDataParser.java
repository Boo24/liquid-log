package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.RenderDataSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RenderDataParser implements IDataParser<RenderDataSet> {

    private Pattern renderTimePattern = Pattern.compile("render time (\\d+)");

    @Override
    public int getBufferSize() {
        return 32 * 1024 * 1024;
    }

    @Override
    public void parseLine(String line, RenderDataSet currentData) {
        if (currentData != null)
        {
            Matcher renderTime = renderTimePattern.matcher(line);
            if (renderTime.find())
            {
                currentData.getRenderTimeStatistics().addTime(Double.parseDouble(renderTime.group(1).trim()));
            }
        }
    }
}
