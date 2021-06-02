package fr.alternalis.orangefactory.elements;

import fr.alternalis.orangefactory.logger.Logger;

public class Valve
{

    private final Double maxOut = 200D;
    private final Double minOut = 1D;
    private Double debit = 0D;

    private static final Integer latencyValve = 1000;

    public static Thread threadValve;

    public Double getDebit()
    {
        return debit;
    }

    public void setDebit(Double debit)
    {
        if(threadValve != null){
            threadValve.interrupt();
        }
        threadValve = new Thread(() -> applyDebit(debit));
        threadValve.start();
        Logger.writeLog("Action", "Vanne", "Débit de la vanne : " + debit);
    }

    public void applyDebit(Double debit){
        try {
            Thread.sleep(latencyValve);
            if(threadValve.isInterrupted()) return;
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

    public Double getMaxOut() {
        return maxOut;
    }

    public Double getMinOut() {
        return minOut;
    }
}
