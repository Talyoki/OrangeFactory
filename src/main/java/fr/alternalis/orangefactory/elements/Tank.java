package fr.alternalis.orangefactory.elements;

public class Tank
{
    private Double temp = 21D;
    private Double level = 500D;
    private Boolean fullAlarm;
    private Boolean emptyAlarm;

    private Boolean active = true;

    private Double enteringAmount = 0D;

    private static final Double LEVEL_MAX = 1000D;
    private static final Double LEVEL_FULL_ALARM = 900D;
    private static final Double LEVEL_EMPTY_ALARM = 100D;

    public Juice generateJuice(Double valveSize){
        if(valveSize > level){
            valveSize = level;
        }
        if(level < 0){
            return null;
        }
        removeJuice(valveSize);
        return new Juice(valveSize, temp);
    }

    public void enteringJuice(){
        if(active = true){
            addJuice(enteringAmount);
            Indicator.total = Indicator.total + enteringAmount;
        }
    }

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
        if(level < 0) level = 0D;
    }

    public void checkFullAlarm()
    {
        fullAlarm = level >= LEVEL_FULL_ALARM;
    }

    public void checkEmptyAlarm()
    {
        emptyAlarm = level <= LEVEL_EMPTY_ALARM;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
