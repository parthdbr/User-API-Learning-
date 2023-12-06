package com.swagger.swager.service;

import com.swagger.swager.decorator.getUserBySearchandField;
import com.swagger.swager.model.User;
import com.swagger.swager.repository.UserCriteriaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class UserCriteriaServiceImpl implements UserCriteriaService{

    @Autowired
    UserCriteriaRepository userCriteriaRepository;


    @Override
    public List<User> getUserByUsernameStartsWith(String c) {
        return userCriteriaRepository.getUserByUsernameStartsWith(c);
    }

    @Override
    public Page<User> getUserByAgedBetween(String start, String end, int page, int size) {
        return userCriteriaRepository.getUserByAgedBetween(start,end, page, size);
    }

    @Override
    public List<User> getUserSortedByAge() {
        return userCriteriaRepository.getUserSortedByAge();
    }

    @Override
    public List<User> getUserBySoftDeleted() {
        return userCriteriaRepository.getUserBySoftDeleted();
    }

    @Override
    public List<User> getUserSelectedFields() {
        return userCriteriaRepository.getUserSelectedFields();
    }

    @Override
    public List<User> getUseronSearchCriteria(String char1, String char2) {
        return userCriteriaRepository.getUseronSearchCriteria(char1, char2);
    }

    @Override
    public User getUserByEmail(String email) {
        return userCriteriaRepository.getUserByEmail(email);
    }

    @Override
    public List<User> getUserByGrade(String grade) {
        return userCriteriaRepository.getUserByGrade(grade);
    }

    @Override
    public List<User> getUserOrderByFname(String order, String field) {
        return userCriteriaRepository.getUserOrderByFname(order, field);
    }

    @Override
    public List<User> getUserBySearchInObject(String str) {
        return userCriteriaRepository.getUserBySearchInObject(str);
    }

    @Override
    public Page<User> getUserBySearchAndSort(getUserBySearchandField data) {
        return userCriteriaRepository.getUserBySearchAndSort(data);
    }


}
