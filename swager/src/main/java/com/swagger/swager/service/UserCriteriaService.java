package com.swagger.swager.service;

import com.swagger.swager.decorator.getUserBySearchandField;
import com.swagger.swager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserCriteriaService {

    public List<User> getUserByUsernameStartsWith(String c);

    public Page<User> getUserByAgedBetween(String start, String end, int page, int size);

    public List<User> getUserSortedByAge();


    List<User> getUserBySoftDeleted();

    List<User> getUserSelectedFields();

    List<User> getUseronSearchCriteria(String char1, String char2);

    User getUserByEmail(String email);

    List<User> getUserByGrade(String grade);

    List<User> getUserOrderByFname(String order, String field);

    List<User> getUserBySearchInObject(String str);

    Page<User> getUserBySearchAndSort(getUserBySearchandField data);
}
