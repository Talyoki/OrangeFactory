package fr.alternalis.orangefactory.elements;

public class Tank
{
    private Double temp;
    private Double level = 500D;
    private Boolean fullAlarm;
    private Boolean emptyAlarm;

    private static final Double LEVEL_MAX = 1000D;
    private static final Double LEVEL_FULL_ALARM = 900D;
    private static final Double LEVEL_EMPTY_ALARM = 100D;

    public void addJuice(Double qt)
    {
        level = level + qt;
        if(level > LEVEL_MAX)
        {
            Indicator.overflow = Indicator.overflow + level - LEVEL_MAX;
            Indicator.spoil = Indicator.spoil + level - LEVEL_MAX;
            level = LEVEL_MAX;
        }
    }

    public void removeJuice(Double qt)
    {
        level = level - qt;
        if(level > 0) level = 0D;
    }

    public void checkFullAlarm()
    {
        if(level >= LEVEL_FULL_ALARM) fullAlarm = true;
    }

    public void checkEmptyAlarm()
    {
        if(level <= LEVEL_EMPTY_ALARM) emptyAlarm = true;
    }

    public void checkAllAlarm()
    {
        checkEmptyAlarm();
        checkFullAlarm();
    }

    public Double getTemp()
    {
        return temp;
    }

    public void setTemp(Double temp)
    {
        this.temp = temp;
    }

    public Double getLevel()
    {
        return level;
    }

    public void setLevel(Double level)
    {
        this.level = level;
    }

    public Boolean getFullAlarm()
    {
        return fullAlarm;
    }

    public void setFullAlarm(Boolean fullAlarm)
    {
        this.fullAlarm = fullAlarm;
    }

    public Boolean getEmptyAlarm()
    {
        return emptyAlarm;
    }

    public void setEmptyAlarm(Boolean emptyAlarm)
    {
        this.emptyAlarm = emptyAlarm;
    }

    public static Double getLevelMax()
    {
        return LEVEL_MAX;
    }

    public static Double getLevelFullAlarm()
    {
        return LEVEL_FULL_ALARM;
    }

    public static Double getLevelEmptyAlarm()
    {
        return LEVEL_EMPTY_ALARM;
    }
}
