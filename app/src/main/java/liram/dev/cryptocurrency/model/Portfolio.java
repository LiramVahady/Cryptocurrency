package liram.dev.cryptocurrency.model;

import java.util.HashMap;

public class Portfolio {

    private HashMap<String, Purchase> purchaseList;
    private double portfolioBalance;

    public Portfolio(){}
    public Portfolio(HashMap<String, Purchase> purchaseList, double portfolioBalance) {
        this.purchaseList = purchaseList;
        this.portfolioBalance = portfolioBalance;
    }

    public HashMap<String, Purchase> getPurchaseList() {
        return purchaseList;
    }

    public double getPortfolioBalance() {
        return portfolioBalance;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "purchaseList=" + purchaseList +
                ", portfolioBalance=" + portfolioBalance +
                '}';
    }

    public static class Purchase implements Comparable<Purchase> {

        private Crypto purchaseCrypto;//100$ ->1 = 100$*2=200$
        //100 -> 2 = 100*3 = 300$ ()

        private double amountInvesment;
        private String dateOfPurchase;


        public Purchase(){}

        public Purchase(Crypto purchaseCrypto, double amountInvesment, String dateOfPurchase) {
            this.purchaseCrypto = purchaseCrypto;
            this.amountInvesment = amountInvesment;
            this.dateOfPurchase = dateOfPurchase;
        }

        public Crypto getPurchaseCrypto() {
            return purchaseCrypto;
        }

        public String getDateOfPurchase() {
            return dateOfPurchase;
        }

        public double getAmountInvesment() {
            return amountInvesment;
        }

        @Override
        public int compareTo(Purchase purchase) {
            if (purchaseCrypto.getCurrentPrice() == purchase.purchaseCrypto.getCurrentPrice()) {
                return 0;
            } else if (purchaseCrypto.getCurrentPrice() > purchase.purchaseCrypto.getCurrentPrice()) {
                return 1;
            } else {
                return -1;
            }
        }

        @Override
        public String toString() {
            return "Purchase{" +
                    "purchaseCrypto=" + purchaseCrypto +
                    ", amountInvesment=" + amountInvesment +
                    ", dateOfPurchase='" + dateOfPurchase + '\'' +
                    '}';
        }
    }
}
