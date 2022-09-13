package com.rcjsolutions.SpringAppTest.repository;

import com.rcjsolutions.SpringAppTest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    List<User> findAllByFirstNameAndLastNameAndAge(String firstname, String lastname, Integer age);
    List<User> findAllByFirstNameAndLastName(String firstname, String lastname);
    List<User> findAllByFirstName(String firstname);
    List<User> findAllByLastName(String lastname);
    List<User> findAllByAge(Integer age);
}
