package liram.dev.cryptocurrency.ui.marketcap.currencyDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import liram.dev.cryptocurrency.database.DatabaseService;
import liram.dev.cryptocurrency.database.UsersDatabaseService;
import liram.dev.cryptocurrency.model.Crypto;
import liram.dev.cryptocurrency.model.CryptoHistory;
import liram.dev.cryptocurrency.model.Portfolio;
import liram.dev.cryptocurrency.model.User;
import liram.dev.cryptocurrency.service.ApiService;
import liram.dev.cryptocurrency.service.CallApiService;
import liram.dev.cryptocurrency.service.WebServiceResponse;
import liram.dev.cryptocurrency.utility.Util;
import retrofit2.Call;
import retrofit2.Retrofit;

public class CurrencyDetailsViewModel extends ViewModel {

    public final String BASE_URL = "https://api.coingecko.com/api/";
    private CallApiService apiService = CallApiService.getInstance();
    private Crypto model;
    private Portfolio.Purchase purchase;
    private User currentUser;
    private DatabaseService databaseService = DatabaseService.getInstance();
    private MutableLiveData<String> amountInvesment = new MutableLiveData<>();
    //Live Date properties:
    private MutableLiveData<List<Entry>> entryMutableLiveData;
    private MutableLiveData<List<Long>> unixDateFormatter;
    private MutableLiveData<Boolean> getInvestDialog;
    private MutableLiveData<String> buyCryptoMessage;


    public CurrencyDetailsViewModel(Crypto model) {
        this.model = model;
        System.out.println(model.toString());
        getCurrentUserBalance();
    }

    public MutableLiveData<Boolean> getGetInvestDialog() {
        if (getInvestDialog == null) {
            getInvestDialog = new MutableLiveData<>();
            getGetInvestDialog().setValue(false);
        }
        return getInvestDialog;
    }

    public MutableLiveData<String> getAmountInvesment() {
        amountInvesment = new MutableLiveData<>();
        return amountInvesment;
    }

    public LiveData<List<Entry>> getHistory() {
        if (entryMutableLiveData == null) {
            entryMutableLiveData = new MutableLiveData<>();
            apiService();
        }
        return entryMutableLiveData;
    }

    public MutableLiveData<List<Long>> getUnixDateFormatter() {
        if (unixDateFormatter == null) {
            unixDateFormatter = new MutableLiveData<>();
        }
        return unixDateFormatter;
    }

    public MutableLiveData<String> getBuyCryptoMessage() {
        if (buyCryptoMessage == null) {
            buyCryptoMessage = new MutableLiveData<>();
        }

        return buyCryptoMessage;
    }

    private void apiService() {
        Retrofit retrofit = apiService.getRetrofit(BASE_URL);
        ApiService service = retrofit.create(ApiService.class);
        Call<CryptoHistory> currencyHistory = service.getCurrencyHistory(model.getId(), "usd", "1");
        apiService.apiCallObjectStructure(currencyHistory, new WebServiceResponse() {
            @Override
            public <T> void success(T response) {
                CryptoHistory cryptoHistory = (CryptoHistory) response;
                int counter = 0;
                List<Long> dDates = new ArrayList<>();
                List<Entry> entries = new ArrayList<>();
                for (int i = 0; i < cryptoHistory.getPrices().size(); i++) {
                    long time = Double.valueOf(cryptoHistory.getPrices().get(i).get(0)).longValue();
                    float price = Double.valueOf(cryptoHistory.getPrices().get(i).get(1)).floatValue();
                    entries.add(new Entry(counter, price));
                    counter++;
                    dDates.add(time);
                    entryMutableLiveData.setValue(entries);
                    unixDateFormatter.setValue(dDates);
                }
            }

            @Override
            public void error(String message) {
                System.out.println("Error +" + message);

            }

            @Override
            public void failure(String message) {
                System.out.println("failure +" + message);
            }
        });
    }

    public void getCurrentUserBalance() {

        DatabaseService.getInstance().getCurrentUser(new UsersDatabaseService() {
            @Override
            public void getAllUsers(List<User> users) {

            }

            @Override
            public void getCurrentUser(User user) {
               double userBalance = user.getUserBalance();
                System.out.println(user.getUserBalance() + "current ");

            }

            @Override
            public void errorOccurred() {
                //TODO: Dialog error alert!
            }
        });

    }

    private void validateUserBalance() {
        double valueInvesment = Double.valueOf(amountInvesment.getValue());
        if (currentUser.getUserBalance() >= valueInvesment) {
            updatePortfolioDatabase(model, valueInvesment);
            buyCryptoMessage.setValue("Yor purchase has been success");
        } else {
            buyCryptoMessage.setValue("Your purchase failed - check your current balance");
        }

    }

    private void updatePortfolioDatabase(Crypto model, double valueInvesment) {
        //TODO: Handle current date

    }

    ;

    public void appearDialogInvesmentOnButtonClicked() {
        getInvestDialog.setValue(true);
    }

    public void onUserConfirmPurchase() {

        if (amountInvesment.getValue() == null || amountInvesment.getValue().isEmpty()){
            buyCryptoMessage.setValue("please enter valid message");
            return;
        }

        if (Util.isNumeric(amountInvesment.getValue())) {
           validateUserBalance();
        }

    }



}