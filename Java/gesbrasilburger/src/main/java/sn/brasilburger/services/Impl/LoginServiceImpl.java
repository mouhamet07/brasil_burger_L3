package sn.brasilburger.services.Impl;

import java.util.Optional;

import sn.brasilburger.entity.User;
import sn.brasilburger.repository.LoginRepository;
import sn.brasilburger.services.LoginService;

public class LoginServiceImpl implements LoginService{
    private LoginRepository loginRepository;
    private static LoginServiceImpl instance = null;
    private LoginServiceImpl(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }
    public static LoginServiceImpl getInstance(LoginRepository loginRepository){
        if (instance==null) {
            return instance = new LoginServiceImpl(loginRepository);
        }
        return instance;
    }
    public Optional<User> login(String email, String pwd){
        return loginRepository.getUserByEmail(email)
            .filter(user -> user.getPassword().equals(pwd));
    }
    public boolean signup(User user){
        return loginRepository.addUser(user);
    }
}
