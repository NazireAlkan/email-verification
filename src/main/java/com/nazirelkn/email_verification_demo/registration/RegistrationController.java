package com.nazirelkn.email_verification_demo.registration;

import com.nazirelkn.email_verification_demo.event.RegistrationCompleteEvent;
import com.nazirelkn.email_verification_demo.registration.token.VerificationToken;
import com.nazirelkn.email_verification_demo.registration.token.VerificationTokenRepository;
import com.nazirelkn.email_verification_demo.user.User;
import com.nazirelkn.email_verification_demo.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher publisher, VerificationTokenRepository tokenRepository){
        this.userService = userService;
        this.publisher = publisher;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request){
        User user = userService.registerUser(registrationRequest);

        // publish registration event

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

        return "Success ! Please, check your email for to complete your registration. ";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);

        if(theToken.getUser().isEnabled()){
            return "This account has already been verified, please, login";
        }

        String verificationResult = userService.validateToken(token);

        if(verificationResult.equalsIgnoreCase("valid")){
            return "Email verified succesfully. Now you can login to your account";

        }
        return "Invalid verification token";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
