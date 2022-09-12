package com.rcjsolutions.SpringAppTest.service;

import com.rcjsolutions.SpringAppTest.domain.User;
import com.rcjsolutions.SpringAppTest.exception.ResourceNotFoundException;
import com.rcjsolutions.SpringAppTest.model.UserData;
import com.rcjsolutions.SpringAppTest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    /*
    * This is method is used to check or validate if email is exist;
    * */
    public boolean isEmailExist(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent())
            return true;

        return false;
    }

    /*
     * This is method is used to save User
     * */
    public User save(User user){
        return userRepository.save(user);
    }

    /*
     * This is method is used to return User List
     * */
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    /*
     * This is method is used to delete User
     * */
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    /*
     * This is method is used to get single User
     * */
    public User getUserById(String userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User populateUser(UserData userData){
        User user = new User();
        user.setEmail(userData.getEmail());
        user.setFirstName(userData.getFirstName());
        user.setMiddleName(userData.getMiddleName());
        user.setLastName(userData.getLastName());

        return user;
    }

    public UserData populateUserData(User user){
        UserData userData = UserData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .build();

        return userData;
    }

    public List<UserData> populateUserList(List<User> users){
        List<UserData> usersList = new ArrayList<>();
        for(User u: users){
            UserData userData = UserData.builder()
                    .id(u.getId())
                    .email(u.getEmail())
                    .firstName(u.getFirstName())
                    .middleName(u.getMiddleName())
                    .lastName(u.getLastName())
                    .build();

            usersList.add(userData);
        }


        return usersList;
    }
}
