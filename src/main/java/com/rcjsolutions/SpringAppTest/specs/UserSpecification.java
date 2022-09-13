package com.rcjsolutions.SpringAppTest.specs;

import com.rcjsolutions.SpringAppTest.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> withFirstName(final String name){
        return (root, query, build) -> build.equal(root.get("firstName"), name);
    }

    public static Specification<User> withFirstNameLowerCase(final String name){
        return (root, query, build) ->  build.equal(build.lower(root.get("firstName")), name.toLowerCase());
    }

    public static Specification<User> withFirstNameLowerCaseLike(final String name){
        return (root, query, build) ->  build.like(build.lower(root.get("firstName")), "%"  + name.toLowerCase() + "%");
    }

    public static Specification<User> withLastName(final String name){
        return (root, query, build) -> build.equal(root.get("lastName"), name);
    }

    public static Specification<User> withLastNameLowerCaseLike(final String name){
        return (root, query, build) ->  build.like(build.lower(root.get("lastName")), "%"  + name.toLowerCase() + "%");
    }

    public static Specification<User> withAge(final Integer age){
        return (root, query, build) -> build.equal(root.get("age"), age);
    }

    public static Specification<User> withAgeRange(final Integer fromAge, final Integer toAge){
        return (root, query, build) -> build.and(build.greaterThanOrEqualTo(root.get("age"), fromAge),
                build.lessThanOrEqualTo(root.get("age"), toAge));
    }
}
