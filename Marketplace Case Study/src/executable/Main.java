package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

import elements.BuyingOrder;
import elements.SellingOrder;
import elements.Market;
import elements.Trader;
import elements.Wallet;

public class Main {

    public static Random myRandom;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File(args[0]));

        final long seed = in.nextLong();

        myRandom = new Random(seed);

        final int initial_fee = in.nextInt();

        final int num_of_users = in.nextInt();

        final int num_of_queries = in.nextInt();

        Market market = new Market(initial_fee);
        ArrayList<Trader> traders = new ArrayList<>(num_of_users);

        for (int id = 0; id < num_of_users; id++) {
            final double usd_amount = in.nextDouble();

            final double goldcoin_amount = in.nextDouble();

            traders.add(new Trader(usd_amount, goldcoin_amount));
        }

        int invalid = 0;
        market.setTraders(traders);

        PrintStream out = new PrintStream(args[1]);

        for (int q = 0; q < num_of_queries; q++) {
            final int query = in.nextInt();

            switch (query) {

                case 10: {		// Give buying order of specific price.
                    final int trader_ID = in.nextInt();
                    final double price = in.nextDouble();
                    final double amount = in.nextDouble();
                    final double usd = amount * price;
                    Wallet wallet = traders.get(trader_ID).getWallet();

                    if (trader_ID == 0 || wallet.checkWithdraw(usd)) {
                        market.BuyOrder(new BuyingOrder(trader_ID, price, amount));
                        if (trader_ID != 0) {
                            wallet.blockUSD(usd);
                        }
                    } else {
                        invalid++;
                    }
                    break;
                }

                case 11: {
                    final int trader_ID = in.nextInt();
                    final double amount = in.nextDouble();
                    final double price = market.sellingPrice();
                    Wallet wallet = traders.get(trader_ID).getWallet();

                    if (market.getSellingOrders().isEmpty()) {
                        invalid++;
                    } else if (trader_ID == 0 || wallet.checkWithdraw(amount * price)) {
                        market.BuyOrder(new BuyingOrder(trader_ID, price, amount));
                        if (trader_ID != 0) {
                            wallet.blockUSD(amount * price);
                        }
                    } else {
                        invalid++;
                    }
                    break;
                }

                case 20: {
                    final int trader_ID = in.nextInt();
                    final double price = in.nextDouble();
                    final double amount = in.nextDouble();
                    Wallet wallet = traders.get(trader_ID).getWallet();

                    if (trader_ID == 0 || wallet.checkWithdraw(amount)) {
                        market.SellOrder(new SellingOrder(trader_ID, price, amount));
                    } else {
                        invalid++;
                    }
                    break;
                }

                case 21: {
                    final int trader_ID = in.nextInt();
                    final double amount = in.nextDouble();
                    final double price = market.buyingPrice();
                    Wallet wallet = traders.get(trader_ID).getWallet();

                    if (market.getBuyingOrders().isEmpty()) {
                        invalid++;
                    } else if (trader_ID == 0 || wallet.checkSelling(amount)) {
                        market.SellOrder(new SellingOrder(trader_ID, price, amount));
                        if (trader_ID != 0) {
                            wallet.blockGoldCoin(amount);
                        }
                    } else {
                        invalid++;
                    }
                    break;
                }

                case 3: {		// Deposit a certain amount of dollars to wallet.
                    final int trader_ID = in.nextInt();
                    final double usd = in.nextDouble();
                    traders.get(trader_ID).getWallet().depositDollars(usd);
                    break;
                }

                case 4: {		// Withdraw a certain amount of dollars from wallet.
                    final int trader_ID = in.nextInt();
                    final double usd = in.nextDouble();
                    Wallet wallet = traders.get(trader_ID).getWallet();

                    if(wallet.checkWithdraw(usd))
                        wallet.withdrawDollars(usd);
                    else
                        invalid++;
                    break;
                }

                case 5: {		// Print wallet status.
                    final int trader_ID = in.nextInt();
                    out.println(traders.get(trader_ID));
                    break;
                }





                case 777: {		// Give rewards to all traders.
                    for(Trader trader : traders)
                        trader.getWallet().depositCoins(myRandom.nextDouble() * 10.0);
                    break;
                }

                case 666: {		// Make open market operation.
                    final double price = in.nextDouble();
                    market.makeOpenMarketOperation(price);
                    break; }

                case 500: {		// Print the current market size.
                    out.println(market.marketInfo());
                    break; }

                case 501: {		// Print number of successful transactions.
                    out.println(market.SuccessfulltransactionNumber());
                    break; }

                case 502: {		// Print the number of invalid queries.
                    out.println("Number of invalid queries: " + invalid);
                    break; }

                case 505: {		// Print the current prices.
                    out.println(market.currentPrice());
                    break; }

                case 555: {		// Print all traders� wallet status.
                    for(Trader t : traders)
                        out.println(t);
                    break; }

                default:
                    System.out.println("Invalid query number : " + query);
            }
        }

        out.println(invalid);

        in.close();
        out.close();
    }
}