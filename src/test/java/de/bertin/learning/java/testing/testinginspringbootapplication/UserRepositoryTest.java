package de.bertin.learning.java.testing.testinginspringbootapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    private User user;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        user = new User("John Doe", "john.doe@gmail.com");
    }

    @Test
    void saveUser() {
        User savedUser = userRepository.save(user);
        assertEquals(user, savedUser);
    }

    @Test
    void findById(){
        User savedUser = userRepository.save(user);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertEquals(user, foundUser);
    }
}