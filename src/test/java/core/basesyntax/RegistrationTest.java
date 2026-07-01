package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.exception.RegistrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationTest {

    private StorageDao storage;
    private RegistrationServiceImpl service;
    private User testUser;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storage = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
        testUser = new User();
    }

    @Test
    void register_validUser_ok() {
        testUser.setAge(18);
        testUser.setPassword("1234567");
        testUser.setLogin("LoginLog");

        service.register(testUser);

        assertNotNull(storage.get(testUser.getLogin()));
    }

    @Test
    void register_shortLogin_notOk() {
        testUser.setLogin("login");
        assertThrows(RegistrationException.class, () -> service.register(testUser));
    }

    @Test
    void register_shortPassword_notOk() {
        testUser.setLogin("ValidLog");
        testUser.setPassword("1234");
        testUser.setAge(19);
        assertThrows(RegistrationException.class, () -> service.register(testUser));
    }

    @Test
    void register_youngAge_notOk() {
        testUser.setLogin("ValidLog");
        testUser.setPassword("123456");
        testUser.setAge(15);
        assertThrows(RegistrationException.class, () -> service.register(testUser));
    }

    @Test
    void register_duplicateLogin_notOk() {
        testUser.setLogin("ExistingUser");
        testUser.setPassword("123456");
        testUser.setAge(20);
        service.register(testUser);

        User duplicate = new User();
        duplicate.setLogin("ExistingUser");
        duplicate.setPassword("password");
        duplicate.setAge(25);

        assertThrows(RegistrationException.class, () -> service.register(duplicate));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        testUser.setPassword("123456");
        testUser.setAge(20);

        assertThrows(RegistrationException.class,
                () -> service.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setLogin("ValidLogin");
        testUser.setPassword(null);
        testUser.setAge(20);

        assertThrows(RegistrationException.class,
                () -> service.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setLogin("ValidLogin");
        testUser.setPassword("123456");
        testUser.setAge(null);

        assertThrows(RegistrationException.class,
                () -> service.register(testUser));
    }

}
