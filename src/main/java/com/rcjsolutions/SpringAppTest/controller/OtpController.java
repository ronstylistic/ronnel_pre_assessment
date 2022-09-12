package com.rcjsolutions.SpringAppTest.controller;

import com.rcjsolutions.SpringAppTest.domain.User;
import com.rcjsolutions.SpringAppTest.exception.ResourceNotFoundException;
import com.rcjsolutions.SpringAppTest.model.QRCodeRequest;
import com.rcjsolutions.SpringAppTest.model.VerifyRequest;
import com.rcjsolutions.SpringAppTest.service.OtpManager;
import com.rcjsolutions.SpringAppTest.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/tfa")
public class OtpController {

    private final UserService userService;
    private final OtpManager otpManager;

    public OtpController(UserService userService, OtpManager otpManager) {
        this.userService = userService;
        this.otpManager = otpManager;
    }

    @PostMapping
    public QRCodeRequest setUpTfa(@RequestBody User user) throws ResourceNotFoundException {
        if(user == null) throw new ResourceNotFoundException("Undefined User");
        User dbUser = userService.getUserById(user.getId());


        String secret = otpManager.generateSecret();
        String imageData = otpManager.getUriForImage(secret, user.getEmail());

        // Saving secret
        dbUser.setSecret(secret);
        userService.save(dbUser);

        QRCodeRequest request = new QRCodeRequest();
        request.setImage(imageData);

        return request;
    }

    @PostMapping(value="/verify")
    public void verifyTfa(@RequestBody VerifyRequest request) throws ResourceNotFoundException {

        if(request.getUser() == null) throw new ResourceNotFoundException("Undefined User");

        User dbUser = userService.getUserById(request.getUser().getId());

        if(otpManager.verifyCode(request.getCode(), dbUser.getSecret())) {
            dbUser.setTfa(true);
            userService.save(dbUser);
        }
        else throw new ResourceNotFoundException("Invalid code");
    }

    @PostMapping(value="/disable")
    public void disableTfa(@RequestBody User user) throws ResourceNotFoundException {

        if(user == null) throw new ResourceNotFoundException("Undefined User");

        User dbUser = userService.getUserById(user.getId());
        dbUser.setTfa(false);
        dbUser.setSecret(null);
        userService.save(dbUser);
    }

}
