package com.rcjsolutions.SpringAppTest.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "USERS")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "`PASSWORD`", length = 200)
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "BIRTHDAY", columnDefinition = "DATE")
    private LocalDate birthday;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "POSITION")
    private String position;

    @JsonProperty("TFA")
    @Column(name = "`TFA`", columnDefinition = "boolean default 'false'", nullable = false)
    private boolean tfa;

    @JsonIgnore
    @Column(name = "`SECRET`", length = 64)
    private String secret;
}
