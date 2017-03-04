package com.antonchar.userservice;

import java.time.LocalDateTime;

import com.antonchar.userservice.entities.User;

public class TestDataHelper {

    // BCrypt password hash for '123' password
    private final static String BCRYPT_PWD_HASH = "$2a$04$jzeBznajaIeT5SOVYsx2XOy0h7ryBePQ/LZ4xqWlT4d457BCQjTZq";

    public final static User USER_ADM, USER_SADM, USER_USR_BL, USER_USR;

    static {
        USER_ADM = new User(
            1L, "admin@example.com", BCRYPT_PWD_HASH, "Vasily", "Pupkin", 29, User.Role.ADMIN,
            false, LocalDateTime.of(2004, 10, 19, 10, 23, 54));

        USER_SADM = new User(
            2L, "superadmin@example.com", BCRYPT_PWD_HASH, "Super", "Bratan", 48,
            User.Role.SUPERADMIN, false, LocalDateTime.of(2005, 12, 31, 10, 23, 54));

        USER_USR_BL = new User(
            3L, "user-blocked@example.com", BCRYPT_PWD_HASH, "John", "Doe", 33, User.Role.USER,
            true, LocalDateTime.of(2006, 10, 19, 10, 23, 54));

        USER_USR = new User(
            4L, "user@example.com", BCRYPT_PWD_HASH, "Avo", "Pots", 22, User.Role.USER,
            false, LocalDateTime.of(2007, 12, 31, 10, 23, 54));
    }

    public static User getNewUserAdm(Long id) {
        return getNewUser(id, User.Role.ADMIN);
    }

    public static User getNewUserUsr(Long id) {
        return getNewUser(id, User.Role.USER);
    }

    private static User getNewUser(Long id, User.Role role) {
        return new User(id, "new@example.com", "dummyHash", "new", "user", 33, role,
                false, LocalDateTime.of(2000, 10, 19, 10, 23, 54));
    }
}
