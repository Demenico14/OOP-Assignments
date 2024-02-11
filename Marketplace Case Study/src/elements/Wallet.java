package elements;

public class Wallet {
    private double USD;
    private double GoldCoin;
    private double blockedUSD;
    private double blockedGoldCoin;

    public Wallet(double USD, double GoldCoin){
        this.USD = USD;
        this.GoldCoin = GoldCoin;
        blockedGoldCoin = 0.0;
        blockedUSD = 0.0;
    }

    public void depositDollars(double usd) {
        this.USD += usd;
    }
    public void depositCoins(double goldcoin) {
        this.GoldCoin += goldcoin;
    }

    public void withdrawDollars(double usd) {
        this.USD -= usd;
    }

    public void blockUSD(double usd) {
        this.USD -= usd;
        blockedUSD += usd;
    }
    public void blockGoldCoin(double goldcoin) {
        this.GoldCoin -= goldcoin;
        blockedGoldCoin += goldcoin;
    }

    public void payFromBlockedUSD(double usd) {
        blockedUSD -= usd;
    }

    public void payFromBlockedGoldCoin(double goldcoin) {
        blockedGoldCoin -= goldcoin;
    }

    public void returnUSD(double usd) {
        this.USD += usd;
        blockedUSD -= usd;
    }

    public boolean checkWithdraw(double usd) {
        return usd <= this.USD;
    }

    public boolean checkSelling(double goldcoin) {
        return goldcoin <= this.GoldCoin;
    }

    public boolean checkBlockedUSD(double usd) {
        return usd <= blockedUSD;
    }

    public boolean checkBlockedGoldCoin(double goldcoin) {
        return goldcoin <= blockedGoldCoin;
    }

    @Override
    public String toString() {
        return String.format("$%.5f PQ%.5f", USD + blockedUSD, GoldCoin + blockedGoldCoin);
    }



}
