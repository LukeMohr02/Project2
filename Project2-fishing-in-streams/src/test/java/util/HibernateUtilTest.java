package util;

import com.MohrShaji.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HibernateUtilTest {

    static SessionFactory sf;

    @Test
    @Order(1)
    public void buildSessionFactoryTest() {
        sf = HibernateUtil.getSessionFactory();

        Assertions.assertInstanceOf(SessionFactory.class, sf);
    }

    @Test
    @Order(2)
    public void getSessionFactoryTest() {
        SessionFactory actual = HibernateUtil.getSessionFactory();

        Assertions.assertEquals(sf, actual);

    }

    @Test
    @Order(3)
    public void shutdownTest() {
        Assertions.assertDoesNotThrow(HibernateUtil::shutdown);
    }
}
