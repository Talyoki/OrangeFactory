package fr.alternalis.orangefactory.elements;

public class Processor {

    private final Double tempMinForValidity = 70D;
    private final Double tempMaxForValidity = 80D;

    private Juice thermalExchanger1 = null;
    private Juice thermalExchanger2 = null;
    private Juice thermalExchanger3 = null;
    private Boiler boiler = new Boiler();
    private Tank tank = new Tank();
    private Pump pump = new Pump();
    private Valve valve = new Valve();

    public void mainProcess(){
        tank.enteringJuice();
        thermalExchanger1 = tank.generateJuice(valve.getDebit());
        if(thermalExchanger1 != null && thermalExchanger3 != null){
            thermalExchange(thermalExchanger1,thermalExchanger3);
        }
        if(thermalExchanger2 != null){
            thermalExchangeBoiler(thermalExchanger2);
        }
        switchingSides();
        tank.checkAllAlarm();
        boiler.checkAllAlarm();
    }

    public void thermalExchange(Juice juice1, Juice juice2){
        Double tempDif;
        if(juice1.getTemp() < juice2.getTemp()){
            tempDif = (juice2.getTemp() - juice1.getTemp()) / 2;
            juice2.setTemp(juice2.getTemp() - tempDif);
            juice1.setTemp(juice1.getTemp() + tempDif);
        } else if (juice1.getTemp() > juice2.getTemp()) {
            tempDif = (juice1.getTemp() - juice2.getTemp()) / 2;
            juice2.setTemp(juice2.getTemp() + tempDif);
            juice1.setTemp(juice1.getTemp() - tempDif);
        }
    }

    public void thermalExchangeBoiler(Juice juice){
        Double tempDif;
        if(juice.getTemp() < boiler.getTemp()){
            tempDif = (boiler.getTemp() - juice.getTemp()) / 2;
            juice.setTemp(juice.getTemp() + tempDif);
        } else if (juice.getTemp() > boiler.getTemp()){
            tempDif = (juice.getTemp() - boiler.getTemp()) / 2;
            juice.setTemp(juice.getTemp() - tempDif);
        }
    }

    public void switchingSides(){
        if(thermalExchanger3 != null){
            Indicator.pasteurized = Indicator.pasteurized + thermalExchanger3.getQuantity();
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
            }
            else if(thermalExchanger2.getTemp() > tempMaxForValidity){
                Indicator.spoil = Indicator.spoil + thermalExchanger2.getQuantity();
            } else {
                thermalExchanger3 = thermalExchanger2;
            }
        }
        thermalExchanger2 = thermalExchanger1;
    }
}
