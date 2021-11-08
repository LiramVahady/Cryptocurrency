package liram.dev.cryptocurrency.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

import liram.dev.cryptocurrency.MainActivity;
import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.database.DatabaseService;
import liram.dev.cryptocurrency.database.UsersDatabaseService;
import liram.dev.cryptocurrency.model.User;
import liram.dev.cryptocurrency.utility.UserPreferencesManager;

public class LoginUserViewModel extends AndroidViewModel implements UsersDatabaseService {

    private MutableLiveData<String> registerNotification;
    private MutableLiveData<Boolean> loginNotification;
    private final DatabaseService userDb = DatabaseService.getInstance();
    private List<User> allUsers;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LoginUserViewModel(@NonNull Application application) {
        super(application);
        getAllUsers();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<String> getRegisterNotification(){
        if (registerNotification == null){
            registerNotification = new MutableLiveData<>();


        }

        return registerNotification;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<Boolean> getLoginLiveData(){
        if (loginNotification == null){
            loginNotification = new MutableLiveData<>();


        }
        return loginNotification;
    }


    /*
    When the user try to register you have to valid this userName is not exist in DB.
    actually the userName is unique. therefore I want to prevent duplicate userName.
    In login situation we have to check if the userName that user try to log in with, is exist.
     */

    private boolean isUserAlreadyExist(String userName){
        if ( allUsers != null && allUsers.size() >  0){
            for (int i = 0; i < allUsers.size(); i++) {
                if (allUsers.get(i).getUserName().equals(userName)){
                    return true;
                }
            }
        }
        return false;
    }

    /*
    User Name has to contain at-least 5 char and number
    david50 -> valid userName
    david -> invalid
     */
    private boolean validaUserName(String userName){
        //check length
        if (userName != null || !userName.isEmpty()){
            for (char c: userName.toCharArray()) {
                if (Character.isDigit(c)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validLength(String userDetails, int length){
        if (!userDetails.isEmpty()){
            if (userDetails.length() > length){
                return true;
            }
        }
        return false;
    }

    /*
    password validation - there is many secure option to check password.
    because is demo app I choose the simple way , no complicated
    so password has to contain length 8 char, and one uppercase
     */
    private boolean isContainUpperCase(String password){
        //first option to check upperCase:
        final char[] chars= password.toCharArray();
        for (char c: chars) {
            if (Character.isUpperCase(c)){
                return  true;
            }
        }
        return false;

        //second option to check upperCase:
        // return password.matches(".*[A-Z].*");

        //Third option to check upperCase
        //  Pattern textPattern = Pattern.compile("(?=.*[A-Z])");
       // return textPattern.matcher(password).matches();
    }

    private boolean passwordsEquals(String password, String repeatPassword){

        return password.equals(repeatPassword);
    }

    /*
        Login has to check that specific userName input and password input are matches!
        therefore I'll filter the list by the unique identifier is user name and than is easier to
        get the password and compare it to input password
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean isPasswordMatchToUserName(String inputUserName, String inputPassword){

        List<User> filter = allUsers.stream().filter(user -> user.getUserName().equals(inputUserName)).collect(Collectors.toList());
        for (User user : filter) {
            System.out.println("current user = "+ user);
            if (user.getPassword().equals(inputPassword)){
                Context context = getApplication().getApplicationContext();
                final String USER_ID = user.getUserID();
                 final String USER_ID_KEY = getApplication().getString(R.string.USER_ID);
               UserPreferencesManager.putStringValue(context, USER_ID_KEY, USER_ID);
                return true;
            }
        }

        return false;
    }

    public void createUser(String name, String password, String repeatPassword){
        //user name validate
        boolean userAlreadyExist = isUserAlreadyExist(name);
        boolean validUserName = validaUserName(name);
        boolean validUserNameLength = validLength(name, 5);
        //validation password
        boolean passwordLength = validLength(password, 8);
        boolean containUpperCase = isContainUpperCase(password);
        //repeat password
        boolean passwordsIsEquals = passwordsEquals(password, repeatPassword);

        if (name.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()){
            registerNotification.setValue("Please fill all the fields");
        }else if(userAlreadyExist){
            registerNotification.setValue("User name already exist");
        }else if (!validUserName){
            registerNotification.setValue("User name has to contain at least one number");
        }else if (!validUserNameLength){
            registerNotification.setValue("User details has to contain 5 char");
        }else if (!passwordLength){
            registerNotification.setValue("Password has to contain 8 chars and up!");
        }else if (!containUpperCase){
            registerNotification.setValue("Password has to contain at-least one upper case");
        }else if (!passwordsIsEquals){
            registerNotification.setValue("Password and repeat password are not same");
        }else{
           userDb.writeUserToDatabase(name, password);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllUsers(){
       userDb.getUsersDatabase(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void userLogin(String userName, String password){
        /*
        To compare if the password match to userName we have to filter the allUser list
        by the userName (every account has unique userName).
         */
        boolean isUserExist = isUserAlreadyExist(userName);
        boolean passwordMatchToUserName = isPasswordMatchToUserName(userName, password);

        if (isUserExist && passwordMatchToUserName){
            loginNotification.setValue(true);
        }else{
            loginNotification.setValue(false);
        }

    }

    public void userSignUpLater(){
        AppCompatActivity appCompatActivity = (AppCompatActivity) getApplication().getApplicationContext();
        Intent intent = new Intent(getApplication().getApplicationContext(), MainActivity.class);
        getApplication().startActivity(intent);
        String userLogKey = getApplication().getString( R.string.USER_LOG_PREFERENCES_KEY);
        UserPreferencesManager.putBooleanValue(getApplication(),userLogKey,false);
    }

    @Override
    public void getAllUsers(List<User> users) {
        allUsers = users;
        System.out.println("All " + allUsers);
    }

    @Override
    public void getCurrentUser(User user) {

    }

    @Override
    public void errorOccurred() {

    }
}