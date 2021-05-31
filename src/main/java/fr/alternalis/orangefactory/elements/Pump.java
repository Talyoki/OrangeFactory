package fr.alternalis.orangefactory.elements;

public class Pump {
    private final Double maxOut = 2000D;
    private final Double minOut = 1D;
    private Double debit = 2000D;

    private static final Integer latencyPump = 1000;

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
            Thread.sleep(latencyPump);

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

    public Double getPercentage(){
        return debit / maxOut;
    }
}
