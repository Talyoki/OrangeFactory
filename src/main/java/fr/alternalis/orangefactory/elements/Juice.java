package fr.alternalis.orangefactory.elements;

public class Juice
{
    private Double quantity;
    private Double temp;

    public Juice(Double quantity, Double temp)
    {
        this.quantity = quantity;
        this.temp = temp;
    }

    public Double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Double quantity)
    {
        this.quantity = quantity;
    }

    public Double getTemp()
    {
        return temp;
    }

    public void setTemp(Double temp)
    {
        this.temp = temp;
    }
}
