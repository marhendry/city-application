package com.andersen.task.city.service.impl;

import com.andersen.task.city.model.entity.UserEntity;
import com.andersen.task.city.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testLoadUserByUsernameHappyCaseScenario() {
        UserEntity user = new UserEntity();
        user.setUsername("userTest");
        when(userRepository.findByUsername("userTest")).thenReturn(user);

        UserDetails result = userService.loadUserByUsername("userTest");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void testLoadUserByUsernameNegativeCaseScenario() {
        when(userRepository.findByUsername("userTest")).thenReturn(null);

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userService.loadUserByUsername("userTest"));
    }




}