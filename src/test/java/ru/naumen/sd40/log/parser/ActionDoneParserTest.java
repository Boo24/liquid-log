package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Test;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.SdngDataSet;

public class ActionDoneParserTest {

    @Test
    public void mustParseAddAction() {
        //given
        SdngDataSet dataSet = new SdngDataSet();

        //when
        ActionDoneParser.parseLine("Done(10): AddObjectAction", dataSet);

        //then
        Assert.assertEquals(1, dataSet.getActionsDoneStatistics().getAddObjectActions());
    }

    @Test
    public void mustParseFormActions() {
        //given
        SdngDataSet dataSet = new SdngDataSet();

        //when
        ActionDoneParser.parseLine("Done(10): GetFormAction", dataSet);
        ActionDoneParser.parseLine("Done(1): GetAddFormContextDataAction", dataSet);

        //then
        Assert.assertEquals(2, dataSet.getActionsDoneStatistics().getFormActions());
    }

    @Test
    public void mustParseEditObject() {
        //given
        SdngDataSet dataSet = new SdngDataSet();

        //when
        ActionDoneParser.parseLine("Done(10): EditObjectAction", dataSet);

        //then
        Assert.assertEquals(1, dataSet.getActionsDoneStatistics().getEditObjectsActions());
    }

    @Test
    public void mustParseSearchObject(){
        //given
        SdngDataSet dataSet = new SdngDataSet();

        //when
        ActionDoneParser.parseLine("Done(10): GetPossibleAgreementsChildsSearchAction", dataSet);
        ActionDoneParser.parseLine("Done(10): TreeSearchAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetSearchResultAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetSimpleSearchResultsAction", dataSet);
        ActionDoneParser.parseLine("Done(10): SimpleSearchAction", dataSet);
        ActionDoneParser.parseLine("Done(10): ExtendedSearchByStringAction", dataSet);
        ActionDoneParser.parseLine("Done(10): ExtendedSearchByFilterAction", dataSet);

        //then
        Assert.assertEquals(7, dataSet.getActionsDoneStatistics().getSearchActions());
    }

    @Test
    public void mustParseGetList(){
        //given:
        SdngDataSet dataSet = new SdngDataSet();

        //when:
        ActionDoneParser.parseLine("Done(10): GetDtObjectListAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetPossibleCaseListValueAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetPossibleAgreementsTreeListActions", dataSet);
        ActionDoneParser.parseLine("Done(10): GetCountForObjectListAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetDataForObjectListAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetPossibleAgreementsListActions", dataSet);
        ActionDoneParser.parseLine("Done(10): GetDtObjectForRelObjListAction", dataSet);

        //then:
        Assert.assertEquals(7, dataSet.getActionsDoneStatistics().geListActions());
    }

    @Test
    public void mustParseComment(){
        //given:
        SdngDataSet dataSet = new SdngDataSet();

        //when:
        ActionDoneParser.parseLine("Done(10): EditCommentAction", dataSet);
        ActionDoneParser.parseLine("Done(10): ChangeResponsibleWithAddCommentAction", dataSet);
        ActionDoneParser.parseLine("Done(10): ShowMoreCommentAttrsAction", dataSet);
        ActionDoneParser.parseLine("Done(10): CheckObjectsExceedsCommentsAmountAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetAddCommentPermissionAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetCommentDtObjectTemplateAction", dataSet);

        //then:
        Assert.assertEquals(6, dataSet.getActionsDoneStatistics().getCommentActions());
    }

    @Test
    public void mustParseDtObject(){
        //given:
        SdngDataSet dataSet = new SdngDataSet();

        //when:
        ActionDoneParser.parseLine("Done(10): GetVisibleDtObjectAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetDtObjectsAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetDtObjectTreeSelectionStateAction", dataSet);
        ActionDoneParser.parseLine("Done(10): AbstractGetDtObjectTemplateAction", dataSet);
        ActionDoneParser.parseLine("Done(10): GetDtObjectTemplateAction", dataSet);

        //then:
        Assert.assertEquals(5, dataSet.getActionsDoneStatistics().getDtObjectActions());
    }

}
