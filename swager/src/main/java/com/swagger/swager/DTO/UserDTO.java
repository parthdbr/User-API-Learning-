package com.swagger.swager.DTO;

import com.swagger.swager.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    String fname;

    String lname;

    String contact;

    String city;

    String age;

    String email;

    String password;

    List<Role> roles;

//    List<Role> roles;


}
