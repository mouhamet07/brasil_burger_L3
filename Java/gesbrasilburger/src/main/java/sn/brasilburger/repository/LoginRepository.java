package sn.brasilburger.repository;

import java.util.Optional;

import sn.brasilburger.entity.User;

public interface LoginRepository {
    Optional<User> getUserByEmail(String email);
    boolean addUser(User user);
}
