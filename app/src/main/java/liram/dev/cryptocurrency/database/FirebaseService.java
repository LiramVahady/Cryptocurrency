package liram.dev.cryptocurrency.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseService {

    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private static FirebaseService instance = null;
    private String userId;
    private Iterable<DataSnapshot> allUsers;

    private FirebaseService(){}

    public static FirebaseService getInstance() {
        if (instance == null){
            instance = new FirebaseService();
        }
        return instance;
    }

   public <T> void write(T object){
       DatabaseReference databaseReference = db.getDatabase().getReference().child("Users").push();

   }

}
