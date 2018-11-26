package ru.naumen.sd40.log.parser.parsers.timeParsers;

public interface ITimeParserCreator<TP extends ITimeParser> {
    TP create();

}
