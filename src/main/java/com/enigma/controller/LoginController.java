package com.enigma.controller;

import com.enigma.exception.UnauthorizedException;
import com.enigma.model.User;
import com.enigma.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMappings.AUTH_URL)
public class LoginController {
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity auth(@RequestBody User dataLoginUser){
        Boolean isValidUsername = dataLoginUser.getUsername().equals("admin");
        Boolean isValidPassword = dataLoginUser.getPassword().equals("admin");
        if(isValidUsername && isValidPassword){
            return ResponseEntity.ok().body(jwtUtil.generateToken("hehe"));
        }
        throw new UnauthorizedException();
    }

    @GetMapping(params = {"token"})
    public String validateToken(@RequestParam String token){
        if(jwtUtil.validateToken(token)){
            return token;
        }
        throw new UnauthorizedException();
    }
}
