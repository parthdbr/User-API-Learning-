package com.swagger.swager.repository;

import com.swagger.swager.decorator.getUserBySearchandField;
import com.swagger.swager.model.User;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserCriteriaRepository {

    public List<User> getUserByUsernameStartsWith(String c);

    public Page<User> getUserByAgedBetween(String start_age, String end_age, int page, int size);

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
