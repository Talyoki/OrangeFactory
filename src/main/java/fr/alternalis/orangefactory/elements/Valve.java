package fr.alternalis.orangefactory.elements;

import fr.alternalis.orangefactory.logger.Logger;

public class Valve
{

    private final Double maxOut = 200D;
    private final Double minOut = 1D;
    private Double debit = 20D;

    private static final Integer latencyValve = 1000;

    public Double getDebit()
    {
        return debit;
    }

    public void setDebit(Double debit)
    {
        Thread thread = new Thread(() -> applyDebit(debit));
        thread.start();
        Logger.writeLog("Action", "Vanne", "Débit de la vanne : " + debit);
    }

    public void applyDebit(Double debit){
        try {
            Thread.sleep(latencyValve);

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
            e.printStackTrace();
        }
    }
}
