package elements;

public class Order {
    protected double amount;
    protected double price;
    protected int traderID;

    public Order(int traderID, double amount, double price){
        this.amount = amount;
        this.price = price;
        this.traderID = traderID;
    }

    public double getAmount() {
        return amount * price;
    }

    public double getPrice() {
        return price;
    }

    public int getTraderID() {
        return traderID;
    }

    public double getDollars() {
        return amount * price;
    }
}
