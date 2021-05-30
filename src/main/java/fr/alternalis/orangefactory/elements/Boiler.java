package fr.alternalis.orangefactory.elements;

public class Boiler
{

    private Double temp = 21D;
    private Double power;
    private final Double maxPower = 100D;
    private final Double minPower = 1D;
    private final Double maxTemp = 100D;
    private final Double minTemp = 0D;

    private static final Integer adaptationTime = 5000;

    public void powerChange(Double newPower)
    {
        power = newPower;
        Thread thread = new Thread(() -> tempChange(maxTemp * (newPower / 100)));
        thread.start();
    }

    public void tempChange(Double newTemp)
    {
        try
        {
            wait(adaptationTime);
            temp = newTemp;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public Double getTemp()
    {
        return temp;
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

    public static Integer getAdaptationTime()
    {
        return adaptationTime;
    }
}
