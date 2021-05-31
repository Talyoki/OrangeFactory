package fr.alternalis.orangefactory.elements;

public class Indicator
{
    public static Double total = 500D;
    public static Double overflow = 0D;
    public static Double recycle = 0D;
    public static Double spoil = 0D;
    public static Double pasteurized = 0D;
    public static long startTime;

    public static Double getPercentageProduced(){
        return pasteurized/total;
    }

    public static Double getPercentageSpoiled(){
        return spoil/total;
    }
}
