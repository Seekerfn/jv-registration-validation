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

    private User testUser;
    private StorageDao storage = new StorageDaoImpl();

    private RegistrationServiceImpl service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        testUser = new User();
        service = new RegistrationServiceImpl();
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

}
