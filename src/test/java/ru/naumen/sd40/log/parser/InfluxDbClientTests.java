package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Test;

public class InfluxDbClientTests {
        @Test
        public void mustReturnEmptyDataSet() {
            //given
            IDataBase db = new InfluxDBClient(null);
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
            IDataBase db = new InfluxDBClient(null);
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
            IDataBase db = new InfluxDBClient(null);
            long key = 42;
            //when
            DataSet firstObj = db.get(key);
            key = 43;

            DataSet secondObj = db.get(key);

            //then
            Assert.assertNotEquals(firstObj, secondObj);
        }


    }
