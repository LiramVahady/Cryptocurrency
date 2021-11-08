package liram.dev.cryptocurrency.service;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallApiService {

    public static CallApiService instance = null;

    private CallApiService(){}

    public static CallApiService getInstance(){
        if (instance == null){
            instance = new CallApiService();
        }
        return instance;
    }

    public Retrofit getRetrofit(String BASE_URL){

        return new Retrofit.Builder().
                            baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
    }

    public <T> void apiCallObjectStructure(Call<T> call, WebServiceResponse apiResponse){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, @NotNull Response<T> response) {
                if (response.isSuccessful()){
                    apiResponse.success(response.body());
                }else{
                    apiResponse.error("Error occurred = " + response.toString());

                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                apiResponse.failure(t.getMessage().toString());
            }

        });
    }

    public <T> void apiCallArrayStructure(Call<List<T>> call, WebServiceResponse apiResponse){
        call.enqueue(new Callback<List<T>>() {
            @Override
            public void onResponse(Call<List<T>> call, @NotNull Response<List<T>> response) {
                if (response.isSuccessful()){
                    apiResponse.success(response.body());
                }else{
                    apiResponse.error("Error occurred = " + response.toString());

                }
            }
            @Override
            public void onFailure(Call<List<T>> call, Throwable t) {
                    apiResponse.failure(t.getMessage().toString());
            }

        });
    }

}

