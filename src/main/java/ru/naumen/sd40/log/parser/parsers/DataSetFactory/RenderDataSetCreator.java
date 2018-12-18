package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.springframework.stereotype.Component;

@Component
public class RenderDataSetCreator implements ICreator<RenderDataSet> {

    @Override
    public RenderDataSet create(long key) {
        return new RenderDataSet(key);
    }
}
