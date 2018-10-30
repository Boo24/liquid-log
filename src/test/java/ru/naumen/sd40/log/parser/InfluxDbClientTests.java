package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class InfluxDbClientTests {

        public IDataBaseWriter dbWriter = Mockito.mock(IDataBaseWriter.class);
        @Test
        public void mustReturnEmptyDataSet() {
            //given
            IDataBaseClient db = new InfluxDBClient(dbWriter);
            long key = 42;
            //when

            DataSet secondObj = db.get(key);

            //then
            ErrorParser errors = secondObj.getErrors();
            Assert.assertTrue(secondObj.cpuData().isNan());
            Assert.assertTrue(secondObj.getGc().isNan());
            Assert.assertEquals(0, errors.errorCount+errors.warnCount+errors.fatalCount);
        }
        @Test
        public void mustReturnOldValue() {
            //given
            IDataBaseClient db = new InfluxDBClient(dbWriter);
            long key = 42;
            //when
            DataSet firstObj = db.get(key);

            DataSet secondObj = db.get(key);

            //then
            Assert.assertEquals(firstObj, secondObj);
        }

        @Test
        public void mustReturnDifferentValuesWhenDifferentKeys() {
            //given
            IDataBaseClient db = new InfluxDBClient(dbWriter);
            long key = 42;
            //when
            DataSet firstObj = db.get(key);
            key = 43;
            DataSet secondObj = db.get(key);

            //then
            Assert.assertNotEquals(firstObj, secondObj);
        }

    @Test
    public void mustSaveTwoStringsWhenTwoDifferentKeys() {
        //given
        IDataBaseClient db = new InfluxDBClient(dbWriter);
        long key = 42;
        //when
        db.get(key);
        key = 43;
        db.get(key);
        db.flush();
        //then

        verify(dbWriter, times(2)).save(anyLong(), anyObject());
    }


    }
