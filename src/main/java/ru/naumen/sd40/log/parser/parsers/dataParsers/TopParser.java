package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.ICreator;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.TopDataSetCreator;
import ru.naumen.sd40.log.parser.parsers.IParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TopTimeParser;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TopTimeParserCreator;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("Top")
public class TopParser extends ChunkHandler implements IParser {

    @Inject
    public TopParser(TopDataParser dataParser, TopTimeParserCreator timeParserFactory) {
        super(dataParser, timeParserFactory);
    }

    public void setDateTime(String file){
        Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(file);
        if (!matcher.find())
            throw new IllegalArgumentException();
        ((TopTimeParser)this.timeParser).setDataDate(matcher.group(0).replaceAll("-", ""));
    }

    @Override
    public ICreator getDataSetCreator() {
        return new TopDataSetCreator();
    }
}
