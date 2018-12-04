package ru.naumen.sd40.log.parser.parsers.dataParsers;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.timeParsers.TopTimeParser;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TopParser extends ChunkHandler {

    @Inject
    public TopParser(TopDataParser dataParser, TopTimeParser timeParser) {
        super(dataParser, timeParser);
    }

    public void setDateTime(String file){
        Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(file);
        if (!matcher.find())
            throw new IllegalArgumentException();
        ((TopTimeParser)this.timeParser).setDataDate(matcher.group(0).replaceAll("-", ""));
    }
}
