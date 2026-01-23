package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.UserDocument;
import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.exception.UserAlreadyExistsException;
import com.kirana.finalphase1.repository.AccountRepository;
import com.kirana.finalphase1.repository.mongo.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * The type User service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMongoRepository userRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * PUBLIC USER SIGNUP
     *
     * @param email    the email
     * @param password the password
     */
    @Transactional
    public void signupUser(String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User already registered");
        }

        // Create MongoDB user
        UserDocument user = new UserDocument();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        UserDocument savedUser = userRepository.save(user);

        // Create Postgres account using Mongo ObjectId
        AccountEntity account = new AccountEntity();
        account.setUserId(savedUser.getId().toHexString());
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);
    }

    /**
     * ADMIN CREATION (ADMIN ONLY)
     *
     * @param email    the email
     * @param password the password
     */
    @Transactional
    public void createAdmin(String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        // Create MongoDB admin
        UserDocument admin = new UserDocument();
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ADMIN");

        UserDocument savedAdmin = userRepository.save(admin);

        // Create Postgres account using Mongo ObjectId
        AccountEntity account = new AccountEntity();
        account.setUserId(savedAdmin.getId().toHexString());
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);
    }

    /**
     * PROMOTE EXISTING USER → ADMIN
     *
     * @param email the email
     */
    @Transactional
    public void promoteToAdmin(String email) {

        UserDocument user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        if ("ADMIN".equals(user.getRole())) {
            throw new IllegalStateException("User is already ADMIN");
        }

        user.setRole("ADMIN");
        userRepository.save(user);
    }
}
