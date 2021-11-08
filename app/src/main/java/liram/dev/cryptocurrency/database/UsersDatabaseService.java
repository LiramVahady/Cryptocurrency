package liram.dev.cryptocurrency.database;

import java.util.List;

import liram.dev.cryptocurrency.model.User;

public interface UsersDatabaseService {
    void getAllUsers(List<User> users);
    void getCurrentUser(User user);
    void errorOccurred();
}
