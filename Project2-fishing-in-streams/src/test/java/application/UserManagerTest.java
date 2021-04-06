package application;

import com.MohrShaji.application.UserManager;
import com.MohrShaji.model.User;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserManagerTest {

    UserManager um = new UserManager();
    static User dummy;
    static int id;

    static {
        dummy = new User();

        dummy.setUsername("dummyUN");
        dummy.setPassword("dummyPW");
        dummy.setFirstname("dummyFN");
        dummy.setLastname("dummyLN");
        dummy.setEmail("dummyEmail");
        dummy.setRole_id(48);
    }

    @Test
    @Order(1)
    public void createUserTest() {
        User user = um.createUser(dummy.getUsername(), dummy.getPassword(), dummy.getFirstname(), dummy.getLastname(), dummy.getEmail(), dummy.getRole_id());
        id = user.getUser_id();
        dummy.setUser_id(id);

        Assertions.assertEquals(dummy, user);
    }

    @Test
    @Order(2)
    public void listUsersTest() {
        List<User> list = um.listUsers();

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertNotNull(list.get(0).getAuthorIds());
    }

    @Test
    @Order(3)
    public void updateUserTest() {
        User user1 = um.updateUser(id, "newUsername");
        User user2 = dummy;
        user2.setUsername("newUsername");

        Assertions.assertEquals(user1, user2);
    }

    @Test
    @Order(4)
    public void getByUserIdTest() {
        User user = um.getByUserId(id);

        Assertions.assertEquals(user, dummy);
    }

    @Test
    @Order(5)
    public void deleteUserTest() {
        User user = um.deleteUser(id);

        Assertions.assertEquals(user, dummy);
        Assertions.assertThrows(IllegalArgumentException.class, () -> um.deleteUser(id));
    }
}
