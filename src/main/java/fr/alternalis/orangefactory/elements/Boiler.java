package fr.alternalis.orangefactory.elements;

import fr.alternalis.orangefactory.logger.Logger;

public class Boiler
{

    private Double temp = 21D;
    private Double power;

    private Pump pump = new Pump();

    private final Double maxPower = 100D;
    private final Double minPower = 1D;
    private final Double maxTemp = 150D;
    private final Double minTemp = 0D;

    private static final Double ALARM_OVERLOAD_TEMP = 90D;
    private static final Double ALARM_ECO_TEMP = 40D;
    private Boolean overloadAlarm = false;
    private Boolean ecoAlarm = false;

    private Boolean active = true;

    private static final Integer latencyBoiler = 1000;

    public void powerChange(Double newPower)
    {
        power = newPower;
        Thread thread = new Thread(() -> tempChange(maxTemp * (newPower / 100)));
        thread.start();
        Logger.writeLog("Action", "Chaudière", "Puissance : " + newPower);
    }

    public void tempChange(Double newTemp)
    {
        try
        {
            double timeMax = latencyBoiler * (int)(newTemp - temp);
            timeMax = Math.abs(timeMax);

            double nbCycle = timeMax / Parameter.cycleTime;

            for(int i = 0; i < nbCycle; i++)
            {
                Thread.sleep(latencyBoiler);
                if(temp  < newTemp) temp = temp + 1;
                else if (temp > newTemp) temp = temp - 1;
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void checkOverloadAlarm(){
        overloadAlarm = temp >= ALARM_OVERLOAD_TEMP;
        if(overloadAlarm) Logger.writeLog("Error", "Chaudière", "Température maximale dépassée");
    }

    public void checkEcoAlarm(){
        ecoAlarm = temp <= ALARM_ECO_TEMP;
        if(ecoAlarm) Logger.writeLog("Error", "Chaudière", "Température minimale dépassée");
    }

    public void checkAllAlarm(){
        checkOverloadAlarm();
        checkEcoAlarm();
    }

    public Double getTemp()
    {
        return temp * pump.getPercentage();
    }

    public void setTemp(Double temp)
    {
        this.temp = temp;
    }

    public Double getPower()
    {
        return power;
    }

    public void setPower(Double power)
    {
        if (power > maxPower)
        {
            this.power = maxPower;
        } else if (power < minPower)
        {
            this.power = minPower;
        } else
        {
            this.power = power;
        }
    }

    public static Integer getLatencyBoiler()
    {
        return latencyBoiler;
    }

    public Boolean getOverloadAlarm() {
        return overloadAlarm;
    }

    public void setOverloadAlarm(Boolean overloadAlarm) {
        this.overloadAlarm = overloadAlarm;
    }

    public Boolean getEcoAlarm() {
        return ecoAlarm;
    }

    public void setEcoAlarm(Boolean ecoAlarm) {
        this.ecoAlarm = ecoAlarm;
    }

    public Pump getPump() {
        return pump;
    }

    public void setPump(Pump pump) {
        this.pump = pump;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
