package com.enigma.service;

import com.enigma.model.User;

import java.util.List;

public interface IAuthService {
    Boolean validateToken(String token);

    String login(User userCredential);

    Boolean logout(String token);
}
