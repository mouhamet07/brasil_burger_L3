package sn.brasilburger.services.Impl;

import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
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
    @Override
    public Optional<User> login(String email, String pwd) {
        String hashedPwd = hashPassword(pwd); 
        return loginRepository.getUserByEmail(email)
                .filter(user -> user.getPassword().equals(hashedPwd));
    }
    @Override
    public Optional<User> getGesByMail(String email){
        return loginRepository.getUserByEmail(email)
            .filter(user -> user.getRole().toString().equals("GESTIONNAIRE"));
    }
    @Override
    public boolean signup(User user){
        user.setPassword(hashPassword(user.getPassword()));
        return loginRepository.insert(user);
    }
    @Override
    public boolean updateGes(User user){
        return loginRepository.update(user);
    }
    @Override
    public boolean archiveGes(User user){
        return loginRepository.delete(user);
    }
    @Override
    public List<User> getAllGes(){
        return loginRepository.getAll();
    }
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
