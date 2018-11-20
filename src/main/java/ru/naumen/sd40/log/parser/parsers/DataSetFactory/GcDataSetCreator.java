package ru.naumen.sd40.log.parser.parsers.DataSetFactory;

public class GcDataSetCreator implements ICreator<GcDataSet> {
    @Override
    public GcDataSet create() {
        return new GcDataSet();
    }
}
