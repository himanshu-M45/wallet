package org.example.wallet.Service;

//import org.example.wallet.Exceptions.UserAuthenitcationFailed;
//import org.example.wallet.Exceptions.UserIsNotRegisteredException;
import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    public int register(String name, String password) {
//        String encodedPassword = passwordEncoder.encode(password);
        User savedUser = userRepository.save(new User(name, password));
        try {
            Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            return (Integer) idField.get(savedUser);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to retrieve user ID", e);
        }
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public int findWalletId(int userId) {
        try {
            return userRepository.findWalletIdByUserId(userId);
        } catch (NullPointerException e) {
            throw new WalletNotFoundException("Wallet not found for user with id " + userId);
        }
    }

//    public boolean authenticateUser(int userId, String password){
//        try {
//            String encodedPassword = fetchEncodedPassword(userId);
//            return passwordEncoder.matches(password, encodedPassword);
//        } catch (UserIsNotRegisteredException e) {
//            throw new UserIsNotRegisteredException("User not found");
//        } catch (Exception e) {
//            throw new UserAuthenitcationFailed("Failed to authenticate user");
//        }
//    }
//
//    private String fetchEncodedPassword(int userId) {
//        User user = findById(userId);
//        if (user != null) {
//            try {
//                Field passwordField = User.class.getDeclaredField("password");
//                passwordField.setAccessible(true);
//                return (String) passwordField.get(user);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                throw new RuntimeException("Failed to retrieve user password", e);
//            }
//        }
//        throw new UserIsNotRegisteredException("User not found");
//    }
}
