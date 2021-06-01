package fr.alternalis.orangefactory.elements;

import fr.alternalis.orangefactory.logger.Logger;

import java.util.TimerTask;

public class Processor extends TimerTask {

    private final Double tempMinForValidity = 70D;
    private final Double tempMaxForValidity = 80D;

    private Juice thermalExchanger1 = null;
    private Juice thermalExchanger2 = null;
    private Juice thermalExchanger3 = null;
    private Boiler boiler = new Boiler();
    private Tank tank = new Tank();
    private Valve valve = new Valve();

    @Override
    public void run(){
        if(!tank.getEmptyAlarm())
        {
            tank.enteringJuice();
            thermalExchanger1 = tank.generateJuice(valve.getDebit());
            if(thermalExchanger1 != null && thermalExchanger3 != null){
                thermalExchange();
            }
            if(thermalExchanger2 != null){
                thermalExchangeBoiler();
            }
            switchingSides();
            tank.checkAllAlarm();
            boiler.checkAllAlarm();

            /*System.out.println("Reserve :" + tank.getLevel());
            System.out.println("Pompe :" + boiler.getPump().getDebit());
            System.out.println("Température cuve :" + tank.getTemp());
            if(thermalExchanger1 != null) System.out.println("Température échangeur 1 :" + thermalExchanger1.getTemp());
            if(thermalExchanger2 != null) System.out.println("Température échangeur 2 :" + thermalExchanger2.getTemp());
            if(thermalExchanger3 != null) System.out.println("Température échangeur 3 :" + thermalExchanger3.getTemp());*/
        }
    }

    public void thermalExchange(){
        Double n = 0.2;

        Double thermalExchanger1g = thermalExchanger1.getTemp()*n;
        Double thermalExchanger3g = thermalExchanger3.getTemp()*n;

        if(thermalExchanger1g > thermalExchanger3g)
        {
            thermalExchanger1.setTemp(thermalExchanger1.getTemp() - thermalExchanger3g);
            thermalExchanger3.setTemp(thermalExchanger3.getTemp() + thermalExchanger1g);
        }else
        {
            thermalExchanger1.setTemp(thermalExchanger1.getTemp() + thermalExchanger3g);
            thermalExchanger3.setTemp(thermalExchanger3.getTemp() - thermalExchanger1g);
        }
    }

    public void thermalExchangeBoiler(){
        if(boiler.getActive()){
            Double n = 0.1;

            Double boilerg = boiler.getTemp()*n;

            if(thermalExchanger2.getTemp() < boiler.getTemp()){
                thermalExchanger2.setTemp(thermalExchanger2.getTemp() + boilerg);
            } else if (thermalExchanger2.getTemp() > boiler.getTemp()){
                thermalExchanger2.setTemp(thermalExchanger2.getTemp() - boilerg);
            }
        }
    }

    public void switchingSides(){
        if(thermalExchanger3 != null){
            Indicator.pasteurized = Indicator.pasteurized + thermalExchanger3.getQuantity();
            Logger.writeLog("Info", "Tube vert", "Quantité produite : " + thermalExchanger3.getQuantity());
            thermalExchanger3 = null;
        }
        if(thermalExchanger2 != null){
            if(thermalExchanger2.getTemp() < tempMinForValidity){
                Indicator.recycle = Indicator.recycle + thermalExchanger2.getQuantity();
                if(thermalExchanger2.getTemp() < tank.getTemp()){
                    tank.setTemp(tank.getTemp() - 1);
                }
                if(thermalExchanger2.getTemp() > tank.getTemp()){
                    tank.setTemp(tank.getTemp() + 1);
                }
                tank.addJuice(thermalExchanger2.getQuantity());
                Logger.writeLog("Info", "Tube bleu", "Quantité recyclée : " + thermalExchanger2.getQuantity());
            }
            else if(thermalExchanger2.getTemp() > tempMaxForValidity){
                Indicator.spoil = Indicator.spoil + thermalExchanger2.getQuantity();
                Logger.writeLog("Info", "Tube rouge", "Quantité gachée : " + thermalExchanger2.getQuantity());

            } else {
                thermalExchanger3 = clone(thermalExchanger2);
            }
        }
        thermalExchanger2 = clone(thermalExchanger1);
    }

    private Juice clone(Juice juice) {
        return new Juice(juice.getQuantity(), juice.getTemp());
    }
}
