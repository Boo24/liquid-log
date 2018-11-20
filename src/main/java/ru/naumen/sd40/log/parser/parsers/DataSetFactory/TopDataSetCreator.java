package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

public class TopDataSetCreator implements ICreator<TopDataSet> {

    @Override
    public TopDataSet create() {
        return new TopDataSet();
    }
}
