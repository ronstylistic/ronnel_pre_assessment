package com.rcjsolutions.SpringAppTest.service;

import com.rcjsolutions.SpringAppTest.domain.User;
import com.rcjsolutions.SpringAppTest.exception.ResourceNotFoundException;
import com.rcjsolutions.SpringAppTest.model.Filter;
import com.rcjsolutions.SpringAppTest.model.UserData;
import com.rcjsolutions.SpringAppTest.repository.UserRepository;
import com.rcjsolutions.SpringAppTest.specs.UserSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<User> getUsers(String firstName, String lastName, Integer age){

        if(!Strings.isEmpty(firstName) && !Strings.isEmpty(lastName) && age != null)
            return userRepository.findAllByFirstNameAndLastNameAndAge(firstName, lastName, age);
        if(!Strings.isEmpty(firstName) && !Strings.isEmpty(lastName))
            return userRepository.findAllByFirstNameAndLastName(firstName, lastName);
        else if(!Strings.isEmpty(firstName))
            return userRepository.findAllByFirstName(firstName);
        else if(!Strings.isEmpty(lastName))
            return userRepository.findAllByLastName(lastName);
        else if(age != null)
            return userRepository.findAllByAge(age);
        return userRepository.findAll();
    }

    public List<User> getUserSpecs(Filter filter){
        UserSpecificationBuilder builder = new UserSpecificationBuilder(filter);
        Specification<User> spec = builder.build();

        //PageRequest request = PageRequest.of(1, 10);
        return userRepository.findAll(spec);
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
        user.setBirthday(userData.getBirthday());
        user.setAge(userData.getAge());
        user.setPosition(userData.getPosition());
        return user;
    }

    public UserData populateUserData(User user){
        UserData userData = UserData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday())
                .age(user.getAge())
                .position(user.getPosition())
                .build();

        return userData;
    }

    public List<UserData> populateUserList(List<User> users){
        return users.stream().map(this::populateUserData).collect(Collectors.toList());
    }
}
