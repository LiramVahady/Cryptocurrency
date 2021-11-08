package liram.dev.cryptocurrency.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class CryptoHistory {

    @SerializedName("prices")
    private List<List<Double>> prices;

    public List<List<Double>> getPrices() { return prices; }


    @Override
    public String toString() {
        return "CryptoHistory{" +
                "prices=" + prices +
                '}';
    }

}
