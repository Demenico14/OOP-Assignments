package elements;

public class BuyingOrder extends Order implements Comparable<BuyingOrder> {
    public BuyingOrder(int traderID, double price, double amount) {
        super(traderID, price, amount);
    }

    @Override
    public int compareTo(BuyingOrder e) {
        if (price > e.price) {
            return -1;
        }
        if (price < e.price) {
            return 1;
        }
        if (amount > e.amount) {
            return -1;
        }
        if (amount < e.amount) {
            return 1;
        }
        return Integer.compare(traderID, e.traderID);
    }
}
