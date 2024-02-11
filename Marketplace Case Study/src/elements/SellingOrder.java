package elements;

public class SellingOrder extends Order implements Comparable<SellingOrder> {
    public SellingOrder(int traderID, double price, double amount) {
        super(traderID, price, amount);
    }

    @Override
    public int compareTo(SellingOrder e) {
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
