package User;
import java.util.*;

class UserRepository {
    private final List<User> users = new ArrayList<>();
    
    void save(User user) {
        users.add(user);
    }

    User findById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail() == email) {
                return user;
            }
        }
        return null;
    }

    List<User> findAll() {
        return new ArrayList<>(users);
    }
}
