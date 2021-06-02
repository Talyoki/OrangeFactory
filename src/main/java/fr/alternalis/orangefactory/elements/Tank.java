package fr.alternalis.orangefactory.elements;

import fr.alternalis.orangefactory.Controller;
import fr.alternalis.orangefactory.logger.Logger;
import javafx.application.Platform;

public class Tank
{
    private Double temp = 21D;
    private Double level = 500D;
    private Boolean fullAlarm = false;
    private Boolean almostEmptyAlarm = false;
    private Boolean emptyAlarm = false;

    private Boolean active = true;

    private Double enteringAmount = 0D;

    private static final Double LEVEL_MAX = 1000D;
    private static final Double LEVEL_FULL_ALARM = 900D;
    private static final Double LEVEL_EMPTY_ALARM = 300D;

    private final Controller controller;

    public Tank(Controller controller){
        this.controller = controller;
    }

    public Juice generateJuice(Double valveSize){
        if(valveSize > level){
            valveSize = level;
        }
        if(level < 0){
            return null;
        }
        removeJuice(valveSize);
        Logger.writeLog("Action", "Cuve", "Débit vanne cuve : " + valveSize);
        return new Juice(valveSize, temp);
    }

    public void enteringJuice(){
        if(active){
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
            Platform.runLater(controller::setYellowArrowVisible);
            Logger.writeLog("Info", "Tube jaune", "Quantité gachée : " + (level - LEVEL_MAX));
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
        if(fullAlarm) Logger.writeLog("Error", "Cuve", "Capacité de la cuve dépassée");
    }

    public void checkAlmostEmptyAlarm()
    {
        almostEmptyAlarm = level <= LEVEL_EMPTY_ALARM && level > 0;
        if(almostEmptyAlarm) Logger.writeLog("Error", "Cuve", "Cuve presque vide");
    }

    public void checkEmptyAlarm()
    {
        emptyAlarm = level <= 0D;
        if(emptyAlarm) Logger.writeLog("Error", "Cuve", "Cuve vide");
    }

    public void checkAllAlarm()
    {
        checkAlmostEmptyAlarm();
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

    public Boolean getAlmostEmptyAlarm()
    {
        return almostEmptyAlarm;
    }

    public void setAlmostEmptyAlarm(Boolean almostEmptyAlarm)
    {
        this.almostEmptyAlarm = almostEmptyAlarm;
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

    public Boolean getEmptyAlarm()
    {
        return emptyAlarm;
    }

    public Double getEnteringAmount() {
        return enteringAmount;
    }

    public void setEnteringAmount(Double enteringAmount) {
        this.enteringAmount = enteringAmount;
    }
}
