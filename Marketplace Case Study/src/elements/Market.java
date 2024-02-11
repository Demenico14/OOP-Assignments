package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Market {

    private PriorityQueue<SellingOrder> sellingOrders;
    private PriorityQueue<BuyingOrder> buyingOrders;
    private ArrayList<Transaction> transactions;
    private int fee;
    private int successful_transactions;
    private ArrayList<Trader> traders;

    public Market(int fee){
        sellingOrders = new PriorityQueue<SellingOrder>();
        buyingOrders = new PriorityQueue<BuyingOrder>();
        transactions = new ArrayList<Transaction>();

        this.fee = fee;
        successful_transactions = 0;
        traders = new ArrayList<Trader>();
    }


    public int getFee() {
        return fee;
    }

    public PriorityQueue<SellingOrder> getSellingOrders() {
        return sellingOrders;
    }

    public PriorityQueue<BuyingOrder> getBuyingOrders() {
        return buyingOrders;
    }

    // Setter method for traders that has traders in the market
    public void setTraders(ArrayList<Trader> traders) {
        this.traders = traders;
    }

    // Adds the given buying order and selling order to the buying priority queue.
    public void SellOrder(SellingOrder order){
        sellingOrders.add(order);
    }
    public void BuyOrder(BuyingOrder order){
        buyingOrders.add(order);
    }

    //Returns the price of the top of buying priority queue
    public double buyingPrice() {
        return buyingOrders.isEmpty() ? 0.0 : buyingOrders.peek().getPrice();
    }

    //Returns the price of the top of selling priority queue.

    public double sellingPrice() {
        return sellingOrders.isEmpty() ? 0.0 : sellingOrders.peek().getPrice();
    }


    //The system makes an open market operation and compensates buying or selling orders
    //in order to set the price to the given price.

    public void checkTransactions(ArrayList<Trader> traders) {
        while (buyingPrice() >= sellingPrice() && !buyingOrders.isEmpty() && !sellingOrders.isEmpty()) {
            SellingOrder sOrder = sellingOrders.poll();
            BuyingOrder bOrder = buyingOrders.poll();

            double tradedAmount;
            if (sOrder.getAmount() > bOrder.getAmount()) {
                tradedAmount = bOrder.getAmount();
                sellingOrders.add(new SellingOrder(sOrder.getTraderID(), sOrder.getPrice(), sOrder.getAmount() - tradedAmount));
            } else if (sOrder.getAmount() < bOrder.getAmount()) {
                tradedAmount = sOrder.getAmount();
                buyingOrders.add(new BuyingOrder(bOrder.getTraderID(), bOrder.getPrice(), bOrder.getAmount() - tradedAmount));
            } else {
                tradedAmount = bOrder.getAmount();
            }

            int sellFlag = traders.get(sOrder.getTraderID()).sell(tradedAmount, sOrder.getPrice(), this);
            int buyFlag = traders.get(bOrder.getTraderID()).buy(tradedAmount, sOrder.getPrice(), this);

            if (sellFlag == 1 && buyFlag == 1) {
                successful_transactions++;
            }

            if (bOrder.getPrice() > sOrder.getPrice()) {
                traders.get(bOrder.getTraderID()).getWallet().returnUSD((bOrder.getPrice() - sOrder.getPrice()) * tradedAmount);
            }

            transactions.add(new Transaction(sOrder, bOrder));
        }
    }


    public void makeOpenMarketOperation(double price) {
        while (buyingPrice() >= price && !buyingOrders.isEmpty()) {
            BuyingOrder bOrder = buyingOrders.peek();
            SellOrder(new SellingOrder(0, bOrder.getAmount(), bOrder.getPrice()));
            checkTransactions(traders);
        }
        while (sellingPrice() <= price && !sellingOrders.isEmpty()) {
            SellingOrder sOrder = sellingOrders.peek();
            BuyOrder(new BuyingOrder(0, sOrder.getAmount(), sOrder.getPrice()));
            checkTransactions(traders);
        }
    }

    public String marketInfo() {
        double dollarsTotal = 0.0, coinsTotal = 0.0;
        for (BuyingOrder bOrder : buyingOrders) {
            dollarsTotal += bOrder.getPrice();
        }
        for (SellingOrder sOrder : sellingOrders) {
            coinsTotal += sOrder.getAmount();
        }
        return String.format("Current market size: %.5f %.5f", dollarsTotal, coinsTotal);
    }

    public String currentPrice() {
        final double buyingOrderPrice = buyingPrice();
        final double sellingOrderPrice = sellingPrice();
        double averagePrice;

        boolean isBuyingOrdersEmpty = buyingOrders.isEmpty();
        boolean isSellingOrdersEmpty = sellingOrders.isEmpty();

        if (isBuyingOrdersEmpty && isSellingOrdersEmpty) {
            averagePrice = 0.0;
        } else if (!isBuyingOrdersEmpty && isSellingOrdersEmpty) {
            averagePrice = buyingOrderPrice;
        } else if (isBuyingOrdersEmpty) {
            averagePrice = sellingOrderPrice;
        } else {
            averagePrice = (buyingOrderPrice + sellingOrderPrice) / 2.0;
        }

        return String.format("Current prices: %.5f %.5f %.5f", buyingOrderPrice, sellingOrderPrice, averagePrice);
    }

    public String SuccessfulltransactionNumber() {
        return "Number of successful transactions: " + successful_transactions;
    }





}
