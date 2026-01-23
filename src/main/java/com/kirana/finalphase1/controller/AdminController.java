package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.dto.SignupRequestDTO;
import com.kirana.finalphase1.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type Admin controller.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    /**
     * Instantiates a new Admin controller.
     *
     * @param userService the user service
     */
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * CREATE ADMIN (ONLY ADMIN CAN CALL)
     *
     * @param request the request
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void createAdmin(@RequestBody SignupRequestDTO request) {
        userService.createAdmin(
                request.getEmail(),
                request.getPassword()
        );
    }

    /**
     * PROMOTE USER → ADMIN (ONLY ADMIN CAN CALL)
     *
     * @param email the email
     */
    @PutMapping("/users/{email}/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public void promoteUserToAdmin(@PathVariable String email) {
        userService.promoteToAdmin(email);
    }
}
