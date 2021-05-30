package fr.alternalis.orangefactory.elements;

public class Valve
{

    private final Double maxOut = 200D;
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
}
