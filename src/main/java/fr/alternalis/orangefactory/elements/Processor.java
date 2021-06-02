package fr.alternalis.orangefactory.elements;

import fr.alternalis.orangefactory.Controller;
import fr.alternalis.orangefactory.logger.Logger;
import javafx.application.Platform;

import java.util.TimerTask;

public class Processor extends TimerTask
{

    private static Boolean isRunning = false;

    private final Controller controller;

    private final Double tempMinForValidity = 70D;
    private final Double tempMaxForValidity = 80D;

    private Juice thermalExchanger1 = null;
    private Juice thermalExchanger2 = null;
    private Juice thermalExchanger3 = null;
    private Boiler boiler;
    private Tank tank;
    private Valve valve = new Valve();

    public Processor(Controller controller)
    {
        this.controller = controller;
        boiler = new Boiler(controller);
        tank = new Tank(controller);
    }

    @Override
    public void run()
    {
        if (!tank.getEmptyAlarm() && !isRunning)
        {
            isRunning = true;

            Platform.runLater(controller::setArrowInvisible);

            tank.enteringJuice();
            thermalExchanger1 = tank.generateJuice(valve.getDebit());
            if (thermalExchanger1 != null && thermalExchanger3 != null)
            {
                thermalExchange();
            }
            if (thermalExchanger2 != null)
            {
                thermalExchangeBoiler();
            }
            switchingSides();
            tank.checkAllAlarm();
            boiler.checkAllAlarm();

            Platform.runLater(() -> {
                controller.setLevelTank(tank.getLevel() / 10);
                controller.setTankTempLabel();
                controller.setPasteurizedLabel();
                controller.setSpoilLabel();
                controller.setRecycleLabel();
                controller.updateAlarmLabelAndColor();
            });

            isRunning = false;
        }
    }

    public void thermalExchange()
    {
        Double n = 0.2;

        Double thermalExchanger1g = thermalExchanger1.getTemp() * n;
        Double thermalExchanger3g = thermalExchanger3.getTemp() * n;

        if (thermalExchanger1g > thermalExchanger3g)
        {
            thermalExchanger1.setTemp(thermalExchanger1.getTemp() - thermalExchanger3g);
            thermalExchanger3.setTemp(thermalExchanger3.getTemp() + thermalExchanger1g);
        } else
        {
            thermalExchanger1.setTemp(thermalExchanger1.getTemp() + thermalExchanger3g);
            thermalExchanger3.setTemp(thermalExchanger3.getTemp() - thermalExchanger1g);
        }
    }

    public void thermalExchangeBoiler()
    {
        if (boiler.getActive())
        {
            Double n = 0.1;

            Double boilerg = boiler.getTemp() * n;

            if (thermalExchanger2.getTemp() < boiler.getTemp())
            {
                thermalExchanger2.setTemp(thermalExchanger2.getTemp() + boilerg);
            } else if (thermalExchanger2.getTemp() > boiler.getTemp())
            {
                thermalExchanger2.setTemp(thermalExchanger2.getTemp() - boilerg);
            }
        }
    }

    public void switchingSides()
    {
        if (thermalExchanger3 != null)
        {
            Indicator.pasteurized = Indicator.pasteurized + thermalExchanger3.getQuantity();
            Platform.runLater(controller::setGreenArrowVisible);
            Logger.writeLog("Info", "Tube vert", "Quantité produite : " + thermalExchanger3.getQuantity());
            thermalExchanger3 = null;
        }
        if (thermalExchanger2 != null)
        {
            if (thermalExchanger2.getTemp() < tempMinForValidity)
            {
                Indicator.recycle = Indicator.recycle + thermalExchanger2.getQuantity();
                if (thermalExchanger2.getTemp() < tank.getTemp())
                {
                    tank.setTemp(tank.getTemp() - 1);
                }
                if (thermalExchanger2.getTemp() > tank.getTemp())
                {
                    tank.setTemp(tank.getTemp() + 1);
                }
                tank.addJuice(thermalExchanger2.getQuantity());
                Platform.runLater(controller::setBlueArrowVisible);
                Logger.writeLog("Info", "Tube bleu", "Quantité recyclée : " + thermalExchanger2.getQuantity());
            } else if (thermalExchanger2.getTemp() > tempMaxForValidity)
            {
                Indicator.spoil = Indicator.spoil + thermalExchanger2.getQuantity();
                Platform.runLater(controller::setRedArrowVisible);
                Logger.writeLog("Info", "Tube rouge", "Quantité gachée : " + thermalExchanger2.getQuantity());

            } else
            {
                thermalExchanger3 = clone(thermalExchanger2);
            }
        }
        thermalExchanger2 = clone(thermalExchanger1);
    }

    private Juice clone(Juice juice)
    {
        return new Juice(juice.getQuantity(), juice.getTemp());
    }

    public Boiler getBoiler()
    {
        return boiler;
    }

    public Tank getTank()
    {
        return tank;
    }

    public Valve getValve()
    {
        return valve;
    }
}
