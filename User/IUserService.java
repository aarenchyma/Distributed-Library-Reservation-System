package User;
import java.util.List;
// this is what will be exposed for other modules to use
//  Other modules only ever import this interface, 


public interface IUserService {
    User registerUser(String name, String Email);
    User getUser(int userId);
    boolean userExists(int userId);
    List<User> listUsers();
}
