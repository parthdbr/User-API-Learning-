package com.swagger.swager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Document(collection = "userdata")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
     String id;

     String fname;

     String lname;

     String contact;

     String city;

     String age;

     String email;

     String password;

     List<Role> roles;

//     List<Role> roles;

     boolean softDelete;


}
