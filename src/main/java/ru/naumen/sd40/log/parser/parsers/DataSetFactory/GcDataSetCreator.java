package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.springframework.stereotype.Component;

@Component
public class GcDataSetCreator implements ICreator<GcDataSet> {
    @Override
    public GcDataSet create(long key) {
        return new GcDataSet(key);
    }
}
