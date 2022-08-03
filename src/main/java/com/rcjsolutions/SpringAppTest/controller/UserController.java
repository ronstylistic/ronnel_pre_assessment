package com.rcjsolutions.SpringAppTest.controller;

import com.rcjsolutions.SpringAppTest.domain.User;
import com.rcjsolutions.SpringAppTest.exception.EmailAlreadyExistException;
import com.rcjsolutions.SpringAppTest.model.UserData;
import com.rcjsolutions.SpringAppTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody UserData request) throws EmailAlreadyExistException {

        if(userService.isEmailExist(request.getEmail()))
            throw new EmailAlreadyExistException();

        UserData userData = UserData.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .build();

        return userService.save(userService.populateUser(userData));
    }

    @PutMapping(value = "/{id}")
    public UserData updateUser(
            @PathVariable(value = "id") String id,
            @RequestBody UserData request) throws Exception {

        User dbUser = userService.getUserById(id);
        dbUser.setEmail(request.getEmail());
        dbUser.setFirstName(request.getFirstName());
        dbUser.setMiddleName(request.getMiddleName());
        dbUser.setLastName(request.getLastName());
        User user = userService.save(dbUser);

        return userService.populateUserData(user);
    }

    @GetMapping
    public List<UserData> getUsers(){
       List<User> users = userService.getUsers();
       return userService.populateUserList(users);
    }

    @GetMapping(value = "/{id}")
    public UserData getUser(@PathVariable(value = "id") String id) throws Exception {
        User user = userService.getUserById(id);
        return userService.populateUserData(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") String id) {
        userService.deleteUser(id);
    }
}
