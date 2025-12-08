package sn.brasilburger.services;

import java.util.Optional;

import sn.brasilburger.entity.User;

public interface LoginService {
    Optional<User> login(String email, String pwd);
    boolean signup(User user);
}
