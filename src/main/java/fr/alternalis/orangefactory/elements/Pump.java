package fr.alternalis.orangefactory.elements;

public class Pump {
    private final Double maxOut = 20D;
    private final Double minOut = 1D;
    private Double debit = 20D;

    public Double getDebit()
    {
        return debit;
    }

    public void setDebit(Double debit)
    {
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
    }

    public Double getPercentage(){
        return debit / maxOut;
    }
}
