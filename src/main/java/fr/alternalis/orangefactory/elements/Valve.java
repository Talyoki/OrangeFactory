package fr.alternalis.orangefactory.elements;

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
    }

    public void applyDebit(Double debit){
        try {
            wait(latencyValve);

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
