package com.rcjsolutions.SpringAppTest.controller;

import com.rcjsolutions.SpringAppTest.domain.User;
import com.rcjsolutions.SpringAppTest.exception.AuthUserException;
import com.rcjsolutions.SpringAppTest.exception.EmailAlreadyExistException;
import com.rcjsolutions.SpringAppTest.exception.ResourceNotFoundException;
import com.rcjsolutions.SpringAppTest.model.AuthRequest;
import com.rcjsolutions.SpringAppTest.model.AuthenticationResponse;
import com.rcjsolutions.SpringAppTest.model.UserData;
import com.rcjsolutions.SpringAppTest.service.OtpManager;
import com.rcjsolutions.SpringAppTest.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final UserService userService;
    private final OtpManager otpManager;

    public AuthController(UserService userService, OtpManager otpManager) {
        this.userService = userService;
        this.otpManager = otpManager;
    }

    @PostMapping(value = "/register")
    public User register(@RequestBody UserData userData,
                         @RequestParam(value = "password") String password) throws EmailAlreadyExistException {

        if(userService.isEmailExist(userData.getEmail()))
            throw new EmailAlreadyExistException();

        User user = new User();
        user.setEmail(userData.getEmail());
        user.setPassword(password);
        user.setFirstName(userData.getFirstName());
        user.setMiddleName(userData.getMiddleName());
        user.setLastName(userData.getLastName());

        return userService.save(user);
    }

    @PostMapping
    public AuthenticationResponse login(
            @RequestBody AuthRequest request) throws AuthUserException, ResourceNotFoundException {

        AuthenticationResponse response = new AuthenticationResponse();
        User dbUser = null;
        Optional<User> user = userService.findUserByEmail(request.getEmail());

        if(user.isPresent()){
            dbUser = user.get();

            if(!dbUser.getPassword().equalsIgnoreCase(request.getPassword()))
                throw new AuthUserException("Password was incorrect");

            if(dbUser.isTfa()){
                if(Strings.isEmpty(request.getCode()))
                    throw new AuthUserException("TFA enabled, Enter OTP code");

                if (otpManager.verifyCode(request.getCode(), dbUser.getSecret())) {
                    User u = userService.getUserById(dbUser.getId());
                    response = getAuthenticationResponse(u);
                }
            }
            else {
                response = getAuthenticationResponse(dbUser);
            }

        }else throw new AuthUserException();

       return response;
    }

    private AuthenticationResponse getAuthenticationResponse(User dbUser) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken("12345678900");
        response.setProfile(dbUser);

        return response;
    }

}
