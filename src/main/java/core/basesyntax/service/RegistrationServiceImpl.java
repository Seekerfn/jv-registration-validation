package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.exception.RegistrationException;

public class RegistrationServiceImpl implements RegistrationService {

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        User existingUser = storageDao.get(user.getLogin());
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RegistrationException("Invalid user data!");
        }
        if (existingUser != null) {
            throw new RegistrationException("The user with this login already exists!");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login length must be more than 6 symbols!");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password length must contain atleast 6 characters!");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User's age must be atleast 18 years old");

        }
        return storageDao.add(user);
    }
}
