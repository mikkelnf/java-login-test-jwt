package com.enigma.service;

import com.enigma.exception.UnauthorizedException;
import com.enigma.model.User;
import com.enigma.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService{
    private List<String> tokenList = new ArrayList<>();;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public String login(User userCredential) {
        Boolean isValidUsername = userCredential.getUsername().equals("admin");
        Boolean isValidPassword = userCredential.getPassword().equals("admin");
        if(isValidUsername && isValidPassword){
            String token = jwtUtil.generateToken("Course");
            tokenList.add(token);
            return token;
        }else{
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    @Override
    public Boolean validateToken(String token) {
        List<String> existingToken = tokenList.stream().filter(x->x.equals(token)).collect(Collectors.toList());

        if(existingToken.isEmpty()){
            throw new UnauthorizedException("Token does not exist");
        }

        if(jwtUtil.validateToken(existingToken.get(0))){
            return true;
        }else{
            throw new UnauthorizedException("Token is invalid");
        }
    }

    @Override
    public Boolean logout(String token) {
        List<String> existingToken = tokenList.stream().filter(x->x.equals(token)).collect(Collectors.toList());

        if(existingToken.isEmpty()){
            throw new UnauthorizedException("Token does not exist");
        }
        if(jwtUtil.validateToken(existingToken.get(0))) {
            tokenList.remove(token);
            return true;
        }
        return false;
    }
}
