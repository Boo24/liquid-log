package ru.naumen.sd40.log.parser.parsers.dataParsers;

import ru.naumen.sd40.log.parser.DataSet;

public class GcDataParser implements IDataParser {

    @Override
    public int getBufferSize() {
        return 8192;
    }

    @Override
    public void parseLine(String line, DataSet currentData) {
        currentData.getGc().parseLine(line);
    }

}
