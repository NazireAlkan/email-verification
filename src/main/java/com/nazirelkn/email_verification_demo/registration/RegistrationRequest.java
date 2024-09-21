package com.nazirelkn.email_verification_demo.registration;

import org.hibernate.annotations.NaturalId;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role) {

}
