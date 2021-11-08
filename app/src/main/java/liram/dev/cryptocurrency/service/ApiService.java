package liram.dev.cryptocurrency.service;


import java.util.List;

import liram.dev.cryptocurrency.model.Convert;
import liram.dev.cryptocurrency.model.Crypto;
import liram.dev.cryptocurrency.model.CryptoHistory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //TODO: Pas url to viewModel
    public final static String BASE_URL = "https://api.coingecko.com/api/";

     @GET("v2/all")
     Call<List<Convert>> getCoins();

     @GET("v3/coins/markets")
     Call<List<Crypto>> getCryptoData(
            @Query("vs_currency") String vsCurrency,
            @Query("order") String order,
            @Query("per_page") String perPage,
            @Query("page") String page,
            @Query("spark_line") boolean sparkLine,
            @Query("price_change_percentage") String pricePercentage
    );

    @GET("v3/coins/{id}/market_chart")
    Call<CryptoHistory>getCurrencyHistory(
    @Path("id") String id,
    @Query("vs_currency") String vs,
    @Query("days") String days
    );
}
