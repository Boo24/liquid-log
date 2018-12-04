package ru.naumen.sd40.log.parser.data;

public class ErrorStatistics {
    private int errorCount;
    private int warnCount;
    private int fatalCount;

    public void changeErrorCount(int value){
        errorCount +=value;
    }

    public void changeWarnCount(int value){
        warnCount +=value;
    }

    public void changeFatalCount(int value){
        fatalCount +=value;
    }

    public int getErrorCount(){
        return errorCount;
    }

    public int getWarnCount(){
        return warnCount;
    }

    public int getFatalCount(){
        return fatalCount;
    }
}
