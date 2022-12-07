package com.enigma.controller;

import com.enigma.exception.UnauthorizedException;
import com.enigma.model.User;
import com.enigma.service.AuthService;
import com.enigma.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMappings.AUTH_URL)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity auth(@RequestBody User dataLoginUser){
        String token = authService.login(dataLoginUser);
        return ResponseEntity.ok(token);
    }

    @GetMapping(params = {"token"})
    public ResponseEntity validateToken(@RequestParam String token){
        if(authService.validateToken(token)){
            return ResponseEntity.ok("Token is valid");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout(@RequestParam String token){
        if(authService.logout(token)){
            return ResponseEntity.ok("Logout success");
        }else{
            return ResponseEntity.ok("Token not found");
        }
    }
}
