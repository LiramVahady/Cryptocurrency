package liram.dev.cryptocurrency.ui.marketcap;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.model.User;
import liram.dev.cryptocurrency.database.DatabaseService;
import liram.dev.cryptocurrency.database.UsersDatabaseService;
import liram.dev.cryptocurrency.utility.UserPreferencesManager;
import liram.dev.cryptocurrency.model.Crypto;
import liram.dev.cryptocurrency.service.ApiService;
import liram.dev.cryptocurrency.service.CallApiService;
import liram.dev.cryptocurrency.service.WebServiceResponse;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MarketCapViewModel extends AndroidViewModel implements WebServiceResponse {

    private MutableLiveData<List<Crypto>> marketList;
    private MutableLiveData<String> textSearch;
    private MutableLiveData<String> errorMessage;
    private CallApiService callApi = CallApiService.getInstance();
    private MutableLiveData<User> currentUser;


    public MarketCapViewModel(@NonNull Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<List<Crypto>> getMarketList() {
        if (marketList == null){
            marketList = new MutableLiveData<>();
           apiService();
        }

        return marketList;
    }

    //TODO Search
    public LiveData<String> getTextSearch(){
        if (textSearch == null){
            textSearch = new MutableLiveData<>();

        }

        return textSearch;
    }

    public LiveData<User> currentUserDetails(){
        if (currentUser == null){
            currentUser = new MutableLiveData<>();
            getCurrentUser();
        }

        return currentUser;
    }

    public LiveData<String> errorMessage(){
        if (currentUser == null){
            errorMessage = new MutableLiveData<>();
            getCurrentUser();
        }

        return errorMessage;
    }
    private void getCurrentUser() {
        DatabaseService.getInstance().getCurrentUser(new UsersDatabaseService() {
            @Override
            public void getAllUsers(List<User> users) {

            }

            @Override
            public void getCurrentUser(User user) {
                if (user != null){
                    currentUser.setValue(user);
                }
            }

            @Override
            public void errorOccurred() {
               // errorMessage.setValue();
            }
        });
    }

    private void apiService(){
        Retrofit retrofit = callApi.getRetrofit(ApiService.BASE_URL);
        ApiService apiService = retrofit.create(ApiService.class);
        String convertType = convertType();
        Call<List<Crypto>> cryptoData = apiService.getCryptoData("USD","market_cap_desc","50", "1", false, "24h");
        callApi.apiCallArrayStructure(cryptoData, this);
    }


    private String convertType() {
        String prefKey = getApplication().getString(R.string.USER_COINS_PREFERENCES_KEY);
        Context context = getApplication();
        String userCoin = UserPreferencesManager.getStringValue(context, prefKey);
        System.out.println(userCoin + "$");
        return userCoin;
    }

    @Override
    public <T> void success(T response) {
        if (response != null){

           List<Crypto> cryptoList = (List<Crypto>) response;
            marketList.setValue(cryptoList);
            System.out.println("esponse = " + response.toString());
        }

    }
    @Override
    public void error(String message) {
        //TODO
        System.out.println("Error = " + message);
    }

    @Override
    public void failure(String message) {
        System.out.println("failure = " + message);
    }
}