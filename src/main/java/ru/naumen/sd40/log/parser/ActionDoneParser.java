package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.SdngDataSet;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by doki on 22.10.16.
 */
@Component
public class ActionDoneParser
{
    private static Set<String> EXCLUDED_ACTIONS = new HashSet<>();

    static
    {
        EXCLUDED_ACTIONS.add("EventAction".toLowerCase());
    }

    private final static Pattern doneRegEx = Pattern.compile("Done\\((\\d+)\\): ?(.*?Action)");

    public static void parseLine(String line, SdngDataSet dataSet)
    {
        Matcher matcher = doneRegEx.matcher(line);

        if (matcher.find())
        {
            String actionInLowerCase = matcher.group(2).toLowerCase();
            if (EXCLUDED_ACTIONS.contains(actionInLowerCase))
            {
                return;
            }
            dataSet.getActionsDoneStatistics().times.add(Integer.parseInt(matcher.group(1)));
            if (actionInLowerCase.equals("addobjectaction"))
            {
                dataSet.getActionsDoneStatistics().changeAddObjectAction(1);
            }
            else if (actionInLowerCase.equals("editobjectaction"))
            {
                dataSet.getActionsDoneStatistics().changeEditObjectAction(1);
            }
            else if (actionInLowerCase.matches("(?i)[a-zA-Z]+comment[a-zA-Z]+"))
            {
                dataSet.getActionsDoneStatistics().changeCommentAction(1);
            }
            else if (!actionInLowerCase.contains("advlist")
                    && actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+List[a-zA-Z]+"))

            {
                dataSet.getActionsDoneStatistics().changeGetListAction(1);
            }
            else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+Form[a-zA-Z]+"))
            {
                dataSet.getActionsDoneStatistics().changeGetFormAction(1);
            }
            else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+DtObject[a-zA-Z]+"))
            {
                dataSet.getActionsDoneStatistics().changeGetDbObjectAction(1);
            }
            else if (actionInLowerCase.matches("(?i)[a-zA-Z]+search[a-zA-Z]+"))
            {
                dataSet.getActionsDoneStatistics().changeSearchAction(1);
            }
            else if (actionInLowerCase.matches("getcatalogsaction"))
            {
                dataSet.getActionsDoneStatistics().changeCatalogsAction(1);
            }

        }
    }
}
