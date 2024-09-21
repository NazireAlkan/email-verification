package com.nazirelkn.email_verification_demo.user;

import com.nazirelkn.email_verification_demo.registration.RegistrationRequest;
import com.nazirelkn.email_verification_demo.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);
    void saveUserVerificationToken(User theUser, String verificationToken);
    String validateToken(String theToken);
}
