package com.andersen.task.city.service.impl;

import com.andersen.task.city.model.RolesToManipulateWithDB;
import com.andersen.task.city.model.entity.UserEntity;
import com.andersen.task.city.repository.UserRepository;
import com.andersen.task.city.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${authorization.username}")
    private String username;

    @Value("${authorization.password}")
    private String password;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @PostConstruct
    public void initializeUser() {
        final UserEntity userEntity = new UserEntity().setUsername(username).setPassword(passwordEncoder.encode(password));
        userEntity.getRolesToManipulateWithDBS().add(RolesToManipulateWithDB.ROLE_ALLOWED_EDIT);
        userRepository.save(userEntity);
    }
}
