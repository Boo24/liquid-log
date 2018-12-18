package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.springframework.stereotype.Component;

@Component
public class TopDataSetCreator implements ICreator<TopDataSet> {

    @Override
    public TopDataSet create(long key) {
        return new TopDataSet(key);
    }
}
