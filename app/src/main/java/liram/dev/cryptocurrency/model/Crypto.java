package liram.dev.cryptocurrency.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Crypto implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("current_price")
    private double currentPrice;
    @SerializedName("market_cap")
    private long marketCap;
    @SerializedName("market_cap_rank")
    private long marketCapRank;
    @SerializedName("fully_diluted_valuation")
    private Long fullyDilutedValuation;
    @SerializedName("total_volume")
    private double totalVolume;
    @SerializedName("high_24h")
    private double high24H;
    @SerializedName("low_24h")
    private double low24H;
    @SerializedName("price_change_24h")
    private double priceChange24H;
    @SerializedName("price_change_percentage_24h")
    private double priceChangePercentage24H;
    @SerializedName("market_cap_change_24h")
    private double marketCapChange24H;
    @SerializedName("market_cap_change_percentage_24h")
    private double marketCapChangePercentage24H;
    @SerializedName("circulating_supply")
    private double circulatingSupply;
    @SerializedName("total_supply")
    private Double totalSupply;
    @SerializedName("max_supply")
    private Double maxSupply;

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public long getMarketCapRank() {
        return marketCapRank;
    }

    public Long getFullyDilutedValuation() {
        return fullyDilutedValuation;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public double getHigh24H() {
        return high24H;
    }

    public double getLow24H() {
        return low24H;
    }

    public double getPriceChange24H() {
        return priceChange24H;
    }

    public double getPriceChangePercentage24H() {
        return priceChangePercentage24H;
    }

    public double getMarketCapChange24H() {
        return marketCapChange24H;
    }

    public double getMarketCapChangePercentage24H() {
        return marketCapChangePercentage24H;
    }

    public double getCirculatingSupply() {
        return circulatingSupply;
    }

    public Double getTotalSupply() {
        return totalSupply;
    }

    public Double getMaxSupply() {
        return maxSupply;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.symbol);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeDouble(this.currentPrice);
        dest.writeLong(this.marketCap);
        dest.writeLong(this.marketCapRank);
        dest.writeValue(this.fullyDilutedValuation);
        dest.writeDouble(this.totalVolume);
        dest.writeDouble(this.high24H);
        dest.writeDouble(this.low24H);
        dest.writeDouble(this.priceChange24H);
        dest.writeDouble(this.priceChangePercentage24H);
        dest.writeDouble(this.marketCapChange24H);
        dest.writeDouble(this.marketCapChangePercentage24H);
        dest.writeDouble(this.circulatingSupply);
        dest.writeValue(this.totalSupply);
        dest.writeValue(this.maxSupply);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.symbol = source.readString();
        this.name = source.readString();
        this.image = source.readString();
        this.currentPrice = source.readDouble();
        this.marketCap = source.readLong();
        this.marketCapRank = source.readLong();
        this.fullyDilutedValuation = (Long) source.readValue(Long.class.getClassLoader());
        this.totalVolume = source.readDouble();
        this.high24H = source.readDouble();
        this.low24H = source.readDouble();
        this.priceChange24H = source.readDouble();
        this.priceChangePercentage24H = source.readDouble();
        this.marketCapChange24H = source.readDouble();
        this.marketCapChangePercentage24H = source.readDouble();
        this.circulatingSupply = source.readDouble();
        this.totalSupply = (Double) source.readValue(Double.class.getClassLoader());
        this.maxSupply = (Double) source.readValue(Double.class.getClassLoader());
    }

    public Crypto() {
    }

    protected Crypto(Parcel in) {
        this.id = in.readString();
        this.symbol = in.readString();
        this.name = in.readString();
        this.image = in.readString();
        this.currentPrice = in.readDouble();
        this.marketCap = in.readLong();
        this.marketCapRank = in.readLong();
        this.fullyDilutedValuation = (Long) in.readValue(Long.class.getClassLoader());
        this.totalVolume = in.readDouble();
        this.high24H = in.readDouble();
        this.low24H = in.readDouble();
        this.priceChange24H = in.readDouble();
        this.priceChangePercentage24H = in.readDouble();
        this.marketCapChange24H = in.readDouble();
        this.marketCapChangePercentage24H = in.readDouble();
        this.circulatingSupply = in.readDouble();
        this.totalSupply = (Double) in.readValue(Double.class.getClassLoader());
        this.maxSupply = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Crypto> CREATOR = new Parcelable.Creator<Crypto>() {
        @Override
        public Crypto createFromParcel(Parcel source) {
            return new Crypto(source);
        }

        @Override
        public Crypto[] newArray(int size) {
            return new Crypto[size];
        }
    };
}
