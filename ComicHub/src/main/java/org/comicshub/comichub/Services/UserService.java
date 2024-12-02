package org.comicshub.comichub.Services;


import lombok.extern.slf4j.Slf4j;
import org.comicshub.comichub.Repositories.UserRepository;
import org.comicshub.comichub.Security.Role;
import org.comicshub.comichub.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(long id){
        return this.userRepository.findById(id);
    }

    public boolean createUser(User user) {
        if(this.userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        this.userRepository.save(user);
        return true;
    }

    public boolean isPasswordsEqual(String first, String second){
        return this.passwordEncoder.matches(first, second);
    }

    public User getUserByPrincipal(Principal principal) {
        if(principal == null) {
            return new User();
        }
        return this.userRepository.findByUsername(principal.getName());
    }

    public User updateUser(User user){
        return this.userRepository.save(user);
    }

}
