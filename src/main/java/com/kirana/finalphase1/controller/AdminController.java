package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.dto.SignupRequestDTO;
import com.kirana.finalphase1.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * CREATE ADMIN (ONLY ADMIN CAN CALL)
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
     */
    @PutMapping("/users/{email}/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public void promoteUserToAdmin(@PathVariable String email) {
        userService.promoteToAdmin(email);
    }
}
