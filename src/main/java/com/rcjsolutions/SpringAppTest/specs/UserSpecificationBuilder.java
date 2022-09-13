package com.rcjsolutions.SpringAppTest.specs;

import com.rcjsolutions.SpringAppTest.domain.User;
import com.rcjsolutions.SpringAppTest.model.Filter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.rcjsolutions.SpringAppTest.specs.UserSpecification.*;

public class UserSpecificationBuilder {
    private final Filter filter;

    public UserSpecificationBuilder(Filter filter){
        this.filter = filter;
    }

    public Specification<User> build(){
        if(filter == null)
            return null;

        List<Specification<User>> specs = new ArrayList<>();

        if(filter.getFirstName() != null){
            specs.add(withFirstName(filter.getFirstName()));
        }

        if(filter.getLastName() != null)
            specs.add(withLastNameLowerCaseLike(filter.getLastName()));

        if(filter.getAge()  != null)
            specs.add(withAge(filter.getAge()));


        if(!specs.isEmpty()){
            Specification<User> result = specs.get(0);

            for(int i=1; i < specs.size(); i++)
                result = Specification.where(result).and(specs.get(i));

            return result;
        }


        return null;
    }
}
