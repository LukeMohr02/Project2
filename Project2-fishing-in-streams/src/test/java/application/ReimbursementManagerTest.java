package application;

import com.MohrShaji.application.ReimbursementManager;
import com.MohrShaji.application.UserManager;
import com.MohrShaji.model.Reimbursement;
import com.MohrShaji.model.User;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReimbursementManagerTest {

    ReimbursementManager rm = new ReimbursementManager();
    static UserManager um = new UserManager();
    static Reimbursement dummy;
    static User dummyUser;
    static int id;
    static int userId;

    static {
        dummyUser = um.createUser("", "", "", "", "", 0);
        userId = dummyUser.getUser_id();

        dummy = new Reimbursement();

        dummy.setAmount(4);
        dummy.setDescription("dummyDSC");
        dummy.setAuthor(dummyUser.getUser_id());
        dummy.setResolver(dummyUser.getUser_id());
        dummy.setStatus_id(8);
        dummy.setType_id(15);
    }

    @Test
    @Order(1)
    public void createReimbursementTest() {
        Reimbursement r = rm.createReimbursement(dummy.getAmount(), dummy.getSubmitted(), dummy.getResolved(), dummy.getDescription(), dummy.getAuthor(), dummy.getResolver(), dummy.getStatus_id(), dummy.getType_id());
        id = r.getId();
        dummy.setId(id);

        Assertions.assertEquals(dummy, r);
    }

    @Test
    @Order(2)
    public void listReimbursementsTest() {
        List<Reimbursement> list = rm.listReimbursements();

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    @Order(3)
    public void updateReimbursementTest() {
        Reimbursement r1 = rm.updateReimbursement(id, 16, dummy.getResolver());
        Reimbursement r2 = dummy;
        r2.setAmount(16);
        r2.setResolver(dummyUser.getUser_id());

        Assertions.assertEquals(r1, r2);
    }

    @Test
    @Order(4)
    public void getByIdTest() {
        Reimbursement r = rm.getById(id);

        Assertions.assertEquals(r, dummy);
    }

    @Test
    @Order(5)
    public void getByUserIdTest() {
        List<Reimbursement> list = rm.getByUserId(userId);

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    @Order(6)
    public void deleteReimbursementTest() {
        Reimbursement r = rm.deleteReimbursement(id);
        um.deleteUser(userId);

        Assertions.assertEquals(r, dummy);
        Assertions.assertThrows(IllegalArgumentException.class, () -> rm.deleteReimbursement(id));
    }
}
