package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Component
public class ErrorParser
{
    static final Pattern warnRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    static final Pattern errorRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    static final Pattern fatalRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    public static boolean checkWarn(String line){
        return warnRegEx.matcher(line).find();
    }

    public static boolean checkError(String line){ return errorRegEx.matcher(line).find(); }

    public static boolean checkFatal(String line){
        return fatalRegEx.matcher(line).find();
    }
}
