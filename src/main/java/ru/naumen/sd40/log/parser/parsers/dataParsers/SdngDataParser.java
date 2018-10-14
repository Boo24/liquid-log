package ru.naumen.sd40.log.parser.parsers.dataParsers;
import ru.naumen.sd40.log.parser.DataSet;

public class SdngDataParser implements IDataParser {

    @Override
    public int getBufferSize() {
        return 32 * 1024 * 1024;
    }

    @Override
    public void parseLine(String line, DataSet currentData) {
        currentData.getActionsDone().parseLine(line);
        currentData.getErrors().parseLine(line);
    }

}
