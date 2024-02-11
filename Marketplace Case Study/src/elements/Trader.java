package elements;

public class Trader {
    private int id;
    private Wallet wallet;
    public static int numberOfUsers = 0;

    public Trader(double USD, double GoldCoin) {
        id = numberOfUsers;
        numberOfUsers++;
        wallet = new Wallet(USD, GoldCoin);
    }

    public int sell(double amount, double price, Market market) {
        final boolean flag = wallet.checkBlockedGoldCoin(amount) || id == 0;

        if(flag) {
            wallet.payFromBlockedGoldCoin(amount);
            wallet.depositDollars(amount * price * (1.0 - (double)market.getFee() / 1000.0));
        }

        return flag ? 1 : 0;
    }
    public int buy(double amount, double price, Market market) {
        final double USD = amount * price;
        final boolean flag = wallet.checkBlockedUSD(USD) || id == 0;

        if(flag) {
            wallet.payFromBlockedUSD(USD);
            wallet.depositCoins(amount);
        }

        return flag ? 1 : 0;
    }

    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return String.format("Trader %d: %s", id, wallet.toString());
    }

}
