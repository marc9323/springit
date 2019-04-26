package com.luv2code.springit.service;

import com.luv2code.springit.domain.User;
import com.luv2code.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
    private final MailService mailService;

    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {

        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
        encoder = new BCryptPasswordEncoder(); //This should be autowired

    }

    public User register(User user){
        // take the password from form and encode
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret); // we are not storing it but need to set it so validation passes when we save it
        // confirm password
        user.setConfirmPassword(secret);

        // assign role to user - for us it will always be of type user
        user.addRole(roleService.findByName("ROLE_USER"));
        // set an activation code
        user.setActivationCode(UUID.randomUUID().toString());
        // disable the user prior to saving because they aren't activated yet - they need to click link in email

        // save user
        save(user);
        // send activation email
        sendActivationEmail(user);
        // return the user
        return user;
    }

    public User save(User user){

        return userRepository.save(user);
    }

    @Transactional
    public void saveUsers(User... users) {
        for(User user : users) {
            logger.info("Saving user: " + user.getEmail());
            userRepository.save(user);
        }
    }

    public void sendActivationEmail(User user){
        mailService.sendActivationEmail(user);
    }

    public void sendWelcomeEmail(User user){
        mailService.sendWelcomeEmail(user);
    }

    public Optional<User> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email, activationCode);
    }

}
