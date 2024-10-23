package org.example.wallet.Service;

import org.example.wallet.Enums.CurrencyType;
import org.example.wallet.Exceptions.UsernameAlreadyRegisteredException;
import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        User user = new User("John Doe", "johnPass", CurrencyType.INR);
        when(userRepository.save(any(User.class))).thenReturn(user);

        String response = userService.register("John Doe", "johnPass", CurrencyType.INR);

        assertEquals("user registered successfully", response);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUsernameAlreadyExists() {
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(UsernameAlreadyRegisteredException.class, () -> {
            userService.register("John Doe", "johnPass", CurrencyType.INR);
        });
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindById() {
        User user = new User("John Doe", "johnPass", CurrencyType.INR);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1);
        assertNotNull(foundUser);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User foundUser = userService.findById(1);
        assertNull(foundUser);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findPasswordByUsername("John Doe")).thenReturn(Optional.of("johnPass"));

        assertDoesNotThrow(() -> {
            userService.loadUserByUsername("John Doe");
        });
        verify(userRepository, times(1)).findPasswordByUsername("John Doe");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findPasswordByUsername("John Doe")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("John Doe");
        });
        verify(userRepository, times(1)).findPasswordByUsername("John Doe");
    }
}