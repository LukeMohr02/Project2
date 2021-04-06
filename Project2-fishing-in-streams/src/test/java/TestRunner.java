import application.ReimbursementManagerTest;
import application.UserManagerTest;
import util.HibernateUtilTest;

public class TestRunner {
    public static void main(String[] args) {
        UserManagerTest umt = new UserManagerTest();
        umt.createUserTest();
        umt.listUsersTest();
        umt.updateUserTest();
        umt.getByUserIdTest();
        umt.deleteUserTest();

        ReimbursementManagerTest rmt = new ReimbursementManagerTest();
        rmt.createReimbursementTest();
        rmt.listReimbursementsTest();
        rmt.updateReimbursementTest();
        rmt.getByIdTest();
        rmt.getByUserIdTest();
        rmt.deleteReimbursementTest();

        HibernateUtilTest hut = new HibernateUtilTest();
        hut.buildSessionFactoryTest();
        hut.getSessionFactoryTest();
        hut.shutdownTest();
    }
}
