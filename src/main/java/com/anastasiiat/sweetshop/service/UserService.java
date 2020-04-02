package com.anastasiiat.sweetshop.service;

import com.anastasiiat.sweetshop.persistence.entity.Role;
import com.anastasiiat.sweetshop.persistence.entity.User;
import com.anastasiiat.sweetshop.persistence.repository.RoleRepository;
import com.anastasiiat.sweetshop.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.SetUtils;

import java.util.Set;

@Service
public class UserService {

    private final String USER_ROLE = "ROLE_USER";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(User user) {
        Set<Role> roles = SetUtils.singletonSet(roleRepository.findByName(USER_ROLE));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (CollectionUtils.isEmpty(user.getRoles())) {
            user.setRoles(roles);
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
