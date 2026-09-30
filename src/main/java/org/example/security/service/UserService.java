package org.example.security.service;

/*
  @author   Tony
  @project   security
  @class  UserService
  @version  1.0.0 
  @since 30.09.2026 - 21.21
*/
import org.example.security.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("1", "john.doe@example.com", "John Doe", "DL12345"));
    }

    public List<User> getAll() { return users; }

    public User getById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public User create(User user) {
        users.add(user);
        return user;
    }

    public User update(String id, User updatedUser) {
        User oldUser = getById(id);
        if (oldUser != null) {
            users.remove(oldUser);
            updatedUser.setId(id);
            users.add(updatedUser);
            return updatedUser;
        }
        return null;
    }

    public void delete(String id) {
        User user = getById(id);
        if (user != null) users.remove(user);
    }
}