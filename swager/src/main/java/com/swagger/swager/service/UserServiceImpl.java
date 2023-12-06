package com.swagger.swager.service;

import com.swagger.swager.DTO.UserDTO;
import com.swagger.swager.Exception.UserExistsException;
import com.swagger.swager.config.NullAwareBeanUtilsBean;
import com.swagger.swager.model.Role;
import com.swagger.swager.model.User;
import com.swagger.swager.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {



    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    NullAwareBeanUtilsBean nullAware;


   @Override
    public Page<User> getAllUserData(int page, int size) {
       Pageable pageable = PageRequest.of(page,size);

        return userRepository.findBySoftDeleteIsFalse(pageable);
    }

    @Override
    public User getUserDataById(String id) {
        return userRepository.findByIdAndSoftDeleteIsFalse(id);
    }

    @Override
    public User createUser(@NotNull UserDTO userDTO) throws UserExistsException {
        User userExists = userRepository.findByEmailContainingAndSoftDeleteIsFalse(userDTO.getEmail());
        log.info("user exists:{}",userExists);
        log.info("inside service:");
        if (ObjectUtils.isEmpty(userExists)) {

            User user = modelMapper.map(userDTO, User.class);


            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(List.of(Role.ADMIN));
            } else if (user.getRoles().contains(Role.USER)) {
                user.setRoles(List.of(Role.USER));
            } else if (user.getRoles().contains(Role.ADMIN)) {
                user.setRoles(List.of(Role.ADMIN));
            } else if (new HashSet<>(user.getRoles()).containsAll(List.of(Role.ADMIN, Role.USER))){
                user.setRoles(Arrays.asList(Role.ADMIN, Role.USER));
            }

//            user.getRoles().replaceAll(String::toUpperCase);

          /*  if(user.getRoles() == null || user.getRoles().isEmpty()){
                user.setRoles(Arrays.asList("USER"));
            } else if (user.getRoles().equalsIgnoreCase("USER") ) {
                user.setRole("USER");
            }else if (user.getRole().equalsIgnoreCase("ADMIN") ) {
                    user.setRole("ADMIN");
            }*/

            return userRepository.save(user);

        }else {
            log.info("User Exists");
            throw new UserExistsException("User Already exists with this Email");
        }
}


    @Override
    public User updateUser(String id, @NotNull UserDTO userDTO) throws InvocationTargetException, IllegalAccessException {
        User userdata = userRepository.findByIdAndSoftDeleteIsFalse(id);

        nullAware.copyProperties(userdata,userDTO);

//        userdata.getRoles().replaceAll(String::toUpperCase);

        return userRepository.save(userdata);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findByIdAndSoftDeleteIsFalse(id);
        if (!ObjectUtils.isEmpty(user)) {
            user.setSoftDelete(true);
            userRepository.save(user);
        }
    }

    @Override
    public List<User> findByAgeSorted() {
        return userRepository.findBySoftDeleteIsFalseOrderByAgeAsc();
    }

   /* @Override
    public List<User> findByGrade(String grade) {
        return userRepository.findBySoftDeleteIsFalseAndGradesIn(grade);
    }
*/
    @Override
    public List<User> findByFirstNameSort(String fname  ) {
        return userRepository.findBySoftDeleteIsFalseAndFnameIgnoreCaseOrderByFnameAsc(fname);   }

    @Override
    public List<User> findByLastNameSort(String lname ) {
        return userRepository.findBySoftDeleteIsFalseAndLnameIgnoreCaseOrderByLnameAsc(lname);
    }

    @Override
    public List<User> findByEmailSort( ) {
        return userRepository.findBySoftDeleteIsFalseOrderByEmailAsc();
    }
}
