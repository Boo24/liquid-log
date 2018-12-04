package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.sd40.log.parser.data.ErrorStatistics;
import ru.naumen.sd40.log.parser.parsers.DataSetFactory.*;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class InfluxDbClientTests {

        public IDataBaseWriter dbWriter = Mockito.mock(IDataBaseWriter.class);
        @Test
        public void mustReturnEmptyDataSet() {
            //given
            IDataBaseClient db = new InfluxDBClient(dbWriter, new GcDataSetCreator());
            long key = 42;
            //when


            GcDataSet secondObj = (GcDataSet) db.get(key);

            //then
            Assert.assertTrue(secondObj.getGcStatistics().isNan());

        }
        @Test
        public void mustReturnOldValue() {
            //given
            IDataBaseClient db = new InfluxDBClient(dbWriter, new SdngDataSetCreator());
            long key = 42;
            //when
            SdngDataSet firstObj = (SdngDataSet)db.get(key);

            SdngDataSet secondObj = (SdngDataSet)db.get(key);

            //then
            Assert.assertEquals(firstObj, secondObj);
        }

        @Test
        public void mustReturnDifferentValuesWhenDifferentKeys() {
            //given
            IDataBaseClient db = new InfluxDBClient(dbWriter, new SdngDataSetCreator());
            long key = 42;
            //when
            IDataSet firstObj = db.get(key);
            key = 43;
            IDataSet secondObj = db.get(key);

            //then
            Assert.assertNotEquals(firstObj, secondObj);
        }

    @Test
    public void mustSaveTwoStringsWhenTwoDifferentKeys() {
        //given
        IDataBaseClient db = new InfluxDBClient(dbWriter, new SdngDataSetCreator());
        long key = 42;
        //when
        db.get(key);
        key = 43;
        db.get(key);
        db.flush();
        //then

        verify(dbWriter, times(2)).save(anyLong(), (SdngDataSet) anyObject());
    }


    }
