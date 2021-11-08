package liram.dev.cryptocurrency.ui.userProfile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import liram.dev.cryptocurrency.model.User;
import liram.dev.cryptocurrency.database.DatabaseService;
import liram.dev.cryptocurrency.database.UsersDatabaseService;
import liram.dev.cryptocurrency.utility.BalanceEnums;

public class UserProfileViewModel extends AndroidViewModel{

    //LiveData properties:
    private MutableLiveData<User> currentUser;
    private MutableLiveData<Double> userBalance;
    private final DatabaseService databaseService = DatabaseService.getInstance();
    private User user;
    private Double balance;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);
        System.out.println("ProfileViewModel ");
    }

    public MutableLiveData<User> getCurrentUser(){

        if (currentUser == null){
            currentUser = new MutableLiveData<>();
            getCurrentUserDatabase();

        }
        return currentUser;
    }

    public MutableLiveData<Double> getUpdateBalance(){
        if (userBalance == null){
            userBalance = new MutableLiveData<>();
        }

        return userBalance;
    }

    private void getCurrentUserDatabase(){
       databaseService.getCurrentUser(new UsersDatabaseService() {
           @Override
           public void getAllUsers(List<User> users) {

           }

           @Override
           public void getCurrentUser(User user) {
               currentUser.setValue(user);
               System.out.println("Current = " + user.toString());
           }
           @Override
           public void errorOccurred() {
               System.out.println("Error occurred");
           }
       });
    }

    public void updateUserBalance(double newBalance){
        DatabaseService.getInstance().updateUserBalance(newBalance, BalanceEnums.DEPOSIT);
    }

}
