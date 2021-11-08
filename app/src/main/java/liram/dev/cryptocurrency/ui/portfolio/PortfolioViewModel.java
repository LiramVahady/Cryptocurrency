package liram.dev.cryptocurrency.ui.portfolio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import liram.dev.cryptocurrency.database.DatabaseService;
import liram.dev.cryptocurrency.model.Portfolio;
import liram.dev.cryptocurrency.service.WebServiceResponse;

public class PortfolioViewModel extends ViewModel {

    private MutableLiveData<HashMap<String, Portfolio.Purchase>> userPurchaseList;
    private DatabaseService databaseService = DatabaseService.getInstance();

    public LiveData<HashMap<String, Portfolio.Purchase>> getUserPurchaseList(){

        if (userPurchaseList == null){
            userPurchaseList =new MutableLiveData<java.util.HashMap<String, Portfolio.Purchase>>();
            //getUsersPurchaseList();
        }
        return userPurchaseList;
    }

    private void getUsersPurchaseList(){
        databaseService.getUserPurchaseList(new WebServiceResponse() {
            @Override
            public <T> void success(T response) {
                Portfolio portfolio = (Portfolio) response;
                HashMap<String, Portfolio.Purchase> purchaseList = portfolio.getPurchaseList();
                userPurchaseList.setValue(purchaseList);
            }

            @Override
            public void error(String message) {

            }

            @Override
            public void failure(String message) {

            }
        });

    }

}