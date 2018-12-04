package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

import org.springframework.stereotype.Component;

@Component
public class SdngDataSetCreator implements ICreator<SdngDataSet> {
    @Override

    public SdngDataSet create() {
        return new SdngDataSet();
    }
}
