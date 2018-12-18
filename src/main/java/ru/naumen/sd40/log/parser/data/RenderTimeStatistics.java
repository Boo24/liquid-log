package ru.naumen.sd40.log.parser.data;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;

public class RenderTimeStatistics {
    public ArrayList<Integer> times = new ArrayList<>();
    private double min;
    private double mean;
    private double max;
    private double count;

    private DescriptiveStatistics ds = new DescriptiveStatistics();

    public void addTime(double value){
        ds.addValue(value);
    }

    public void calculate() {
        DescriptiveStatistics ds = new DescriptiveStatistics();
        times.forEach(ds::addValue);
        min = ds.getMin();
        mean = ds.getMean();
        max = ds.getMax();
        count = ds.getN();
    }

    public double getCalculatedAvg()
    {
        return mean;
    }

    public double getMaxRenderTime()
    {
        return max;
    }

    public double getMinRenderTime()
    {
        return min;
    }

    public double getCount()
    {
        return count;
    }
}
