package liram.dev.cryptocurrency.database;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import liram.dev.cryptocurrency.model.Portfolio;
import liram.dev.cryptocurrency.model.User;
import liram.dev.cryptocurrency.service.WebServiceResponse;
import liram.dev.cryptocurrency.utility.BalanceEnums;

public class DatabaseService {

    final private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private static DatabaseService instance = null;
    private String userId;
    private int MIN_LENGTH_USER_ID = 6;
    private double currentBalance;
    Portfolio portfolio;
    HashMap<String, Portfolio.Purchase> list = new HashMap<>();



    private DatabaseService(){}

    public static DatabaseService getInstance() {
        if (instance == null){
            instance = new DatabaseService();
        }

        return instance;
    }

    //Methods handle in user details in Firebase:
    public void writeUserToDatabase(String userName, String password){
        //currentUser = new User(userName, password);
        //push create autoId
        DatabaseReference databaseReference = db.getDatabase().getReference().child("Users").push();
        userId = databaseReference.getKey();
        User user = new User(userName, password, userId, 0.0);
        databaseReference.setValue(user);
        System.out.println("Length = " + userId.length());

    }

    public void updateCurrentUserId(String currentId){
        if (!currentId.isEmpty() || currentId.length() <= MIN_LENGTH_USER_ID || currentId != null ){
            this.userId = currentId;
        }
    }

    public void getCurrentUser(UsersDatabaseService databaseService){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Iterable<DataSnapshot> allUsers = snapshot.child("Users").getChildren();
                for (DataSnapshot dataSnapshot : allUsers) {
                    if (dataSnapshot.getKey().equals(userId)){
                        User currentUser = dataSnapshot.getValue(User.class);
                         databaseService.getCurrentUser(currentUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                databaseService.errorOccurred();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getUsersDatabase(UsersDatabaseService response){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> allUsers = snapshot.child("Users").getChildren();
                List<User> userList = new ArrayList<>();
                for (DataSnapshot child : allUsers) {
                    User user = child.getValue(User.class);
                    userList.add(user);
                    response.getAllUsers(userList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                response.error(error.getMessage());
            }
        });
    }

    public void updateUserBalance(double newBalance, BalanceEnums balanceEnums){
        db.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 currentBalance = snapshot.child("userBalance").getValue(Double.class);
                 switch (balanceEnums){
                     case DEPOSIT:
                         currentBalance += newBalance;
                         snapshot.getRef().child("userBalance").setValue(currentBalance);
                         break;
                     case PURCHASE:
                         currentBalance -= newBalance;
                         snapshot.getRef().child("userBalance").setValue(currentBalance);
                         break;
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Handle in Portfolio Firebase when user perform purchase
    public void writePortfolioData(Portfolio.Purchase purchase){
        list.values().add(purchase);
        portfolio = new Portfolio(list,0.0);
        db.getDatabase().getReference().child("Users").child(userId).child("portfolio").setValue(portfolio);
    }

    public void updateCurrentPurchase(double amount){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot child = snapshot.child("Users").child(userId).child("portfolio").child("list");
              // System.out.println(child);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUserPurchaseList(WebServiceResponse webServiceResponse){
        db.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot portfolio = snapshot.child("portfolio");
                System.out.println(portfolio);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

