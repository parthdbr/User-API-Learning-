package com.swagger.swager.service;



import com.swagger.swager.DTO.UserDTO;
import com.swagger.swager.Exception.UserExistsException;
import com.swagger.swager.model.User;
import org.springframework.data.domain.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService {

    public Page<User> getAllUserData(int page, int size);

    public User getUserDataById(String id);

    public User createUser (UserDTO userDTO) throws UserExistsException;

    public User updateUser(String id, UserDTO userDTO) throws InvocationTargetException, IllegalAccessException;

    public void deleteUser(String id);


    List<User> findByAgeSorted();

    /*List<User> findByGrade(String grade);*/

    List<User> findByFirstNameSort( String fname );

    List<User> findByLastNameSort( String lname);

    List<User> findByEmailSort( );
}
