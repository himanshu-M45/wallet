package org.example.wallet.Service;

import org.example.wallet.Enums.CurrencyType;
import org.example.wallet.Exceptions.UserNotAuthenticatedException;
import org.example.wallet.Exceptions.UserNotAuthorizedException;
import org.example.wallet.Exceptions.UsernameAlreadyRegisteredException;
import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public String register(String username, String password, CurrencyType currencyType) {
        try {
            userRepository.save(new User(username, password, currencyType));
            return "user registered successfully";
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyRegisteredException("username already exists");
        }
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        String retrievedPassword = userRepository.findPasswordByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(username + " no password associated with provided username")
                );

        return new org.springframework.security.core.userdetails.User(
                username,
                retrievedPassword,
                Collections.singletonList(() -> "ROLE_USER")
        );
    }

    public void isUserAuthorized(Integer userId, Integer walletId) {
        if (userRepository.findWalletIdByUserId(userId).equals(walletId) && checkUserAuthentication(userId)) {
            return;
        }
        throw new UserNotAuthorizedException("user not authorised");
    }

    private User getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername()).orElse(null);
    }

    private boolean checkUserAuthentication(Integer userId) {
        User authenticatedUser = getAuthenticatedUser();
        User user = findById(userId);
        if (user != null && authenticatedUser != null) {
            return authenticatedUser == user;
        }
        throw new UserNotAuthenticatedException("user not authenticated");
    }
}
