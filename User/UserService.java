package User;

import java.util.List;

public class UserService implements IUserService {
    private final UserRepository userRepository = new UserRepository();
    private int nextId = 1;

    @Override
    public User registerUser(String name, String email) {
        User user = new User(nextId++, name, email);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean userExists(int userId) {
        return userRepository.findById(userId) != null;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
