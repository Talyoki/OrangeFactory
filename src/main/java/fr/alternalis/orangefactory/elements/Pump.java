package fr.alternalis.orangefactory.elements;

import fr.alternalis.orangefactory.logger.Logger;

public class Pump {
    private final Double maxOut = 2000D;
    private final Double minOut = 1D;
    private Double debit = 0D;

    private static final Integer latencyPump = 1000;

    public static Thread threadPump;

    public Double getDebit()
    {
        return debit;
    }

    public void setDebit(Double debit)
    {
        if(threadPump != null){
            threadPump.interrupt();
        }
        threadPump = new Thread(() -> applyDebit(debit));
        threadPump.start();
        Logger.writeLog("Info", "Pompe", "Débit de la pompe modifié : " + debit);
    }

    public void applyDebit(Double debit){
        try {
            Thread.sleep(latencyPump);
            if(threadPump.isInterrupted()) return;
            if (debit > maxOut)
            {
                this.debit = maxOut;
            } else if (debit < minOut)
            {
                this.debit = minOut;
            } else
            {
                this.debit = debit;
            }
        } catch (InterruptedException e) {
            //Do nothing
        }
    }

    public Double getPercentage(){
        return debit / maxOut;
    }

    public Double getMaxOut() {
        return maxOut;
    }

    public Double getMinOut() {
        return minOut;
    }
}
