package com.swagger.swager.controller;

import com.swagger.swager.decorator.*;
import com.swagger.swager.model.User;
import com.swagger.swager.service.UserCriteriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserCriteriaController {

    @Autowired
    UserCriteriaService userCriteriaService;

    @Operation(summary = "Get users by username starting with the entered character",
            description = "Get list of users starting with the entered character")
    @GetMapping(value = "/criteria/{c}", produces = "application/json")
    public ListDataResponse<User> getUserByUsernameStartsWith(@Parameter(description = "Data will be fetched by username starting with character") @PathVariable String c) {
        List<User> users = userCriteriaService.getUserByUsernameStartsWith(c);
        ListDataResponse response = new ListDataResponse();
        try {
            if (!users.isEmpty()) {
                response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No User found with username starting with " + c, "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;
    }

    @Operation(summary = "Get users by Aged between the values",
            description = "Get list of users aged between the entered values")
    @GetMapping(value = "/criteria/{start}/{end}", produces = "application/json")
    public PageResponse<User> getUserByAgedBetween(@PathVariable("start") String start_age, @PathVariable("end") String end_age, int page, int size) {
        Page<User> users = userCriteriaService.getUserByAgedBetween(start_age, end_age, page, size);
        PageResponse response = new PageResponse();
        try {
            if (!users.isEmpty()) {
                response.setUsers(users.getContent());
                response.setPage_number((long) page);
                response.setSize_of_page((long) size);
                response.setTotal_page((long) users.getTotalPages());
                response.setTotal_count(users.getTotalElements());
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No User found between the entered ages", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No User found between the entered ages", "404"));
        }
        return response;
    }

    @Operation(summary = "Get users Based on sorted ages ",
            description = "Get list of users Based on sorted ages")
    @GetMapping(value = "/criteria/sort", produces = "application/json")
    public ListDataResponse<User> getUserSortedByAge() {
        List<User> users = userCriteriaService.getUserSortedByAge();
        ListDataResponse response = new ListDataResponse();
        try {
            if (!users.isEmpty()) {
                response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;
    }

    @Operation(summary = "Get deleted users  ",
            description = "Get list of deleted users ")
    @GetMapping(value = "/criteria/deleted", produces = "application/json")
    public ListDataResponse<User> getUserBySoftDeleted() {
        List<User> users = userCriteriaService.getUserBySoftDeleted();
        ListDataResponse response = new ListDataResponse();
        try {
            if (!users.isEmpty()) {
                response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;
    }

    @Operation(summary = "Get deleted users  ",
            description = "Get list of deleted users ")
    @GetMapping(value = "/criteria/fields", produces = "application/json")
    public ListDataResponse<User> getUserSelectedFields() {
        List<User> users = userCriteriaService.getUserSelectedFields();
        ListDataResponse response = new ListDataResponse();
        try {
            if (!users.isEmpty()) {
                response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;
    }

    @Operation(summary = "Get user on selected criteria  ",
            description = "Get list of users based on first letter of first name and last name along-with the city matching value")
    @GetMapping(value = "/criteria/search", produces = "application/json")
    public ListDataResponse<User> getUseronSearchCriteria(String char1, String char2) {

        List<User> users = userCriteriaService.getUseronSearchCriteria(char1, char2);
        ListDataResponse<User> response = new ListDataResponse<>();
        try {
            if (users != null) {
                if (!users.isEmpty()) {
                    response.setData(users);
                    response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
                } else {
                    response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

                }
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

    @Operation(summary = "Get user by email  ",
            description = "Get a user by email ")
    @GetMapping(value = "/criteria/findByEmail", produces = "application/json")
    public DataResponse<User> getUserByEmail(String email) {

        User users = userCriteriaService.getUserByEmail(email);
        DataResponse<User> response = new DataResponse<>();
        try {
            if (users != null) {
                if (!ObjectUtils.isEmpty(users)) {
                    response.setData(users);
                    response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
                } else {
                    response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
                }
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

    @Operation(summary = "Get user by Grades  ",
            description = "Get a user by Grades ")
    @GetMapping(value = "/criteria/findByGrades", produces = "application/json")
    public ListDataResponse<User> getUserByGrade(String grade) {

        List<User> users = userCriteriaService.getUserByGrade(grade);
        ListDataResponse<User> response = new ListDataResponse<>();
        try {
            if (users != null) {
                if (!users.isEmpty()) {
                    response.setData(users/*.stream().map(user -> user.getFname()).sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList())*/);
                    response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
                } else {
                    response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
                }
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

    @GetMapping(value = "/criteria/orderByFname", produces = "application/json")

    public ListDataResponse<User> getUserOrderByFname(@RequestParam String order, String field) {

        List<User> users = userCriteriaService.getUserOrderByFname(order, field);
        ListDataResponse<User> response = new ListDataResponse<>();
        try {
            if (users != null) {
                if (!users.isEmpty()) {
                    response.setData(users);
                    response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
                } else {
                    response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
                }
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

    @GetMapping(value = "/criteria/searchInDatabase", produces = "application/json")

    public ListDataResponse<User> getUserBySearchInObject(@RequestParam String str) {

        List<User> users = userCriteriaService.getUserBySearchInObject(str);
        ListDataResponse<User> response = new ListDataResponse<>();
        try {
            if (users != null) {
                if (!users.isEmpty()) {
                    response.setData(users);
                    response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
                } else {
                    response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
                }
            } else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

    @PostMapping(value = "/criteria/getUserBySearchAndSortPaginantion", produces = "application/json")
    public PageResponse<User> getUserBySearchAndSort(@RequestBody getUserBySearchandField data) {
        Page<User> users = userCriteriaService.getUserBySearchAndSort(data);
        PageResponse<User> response = new PageResponse<>();
        try{

            if(!users.isEmpty()) {
                response.setUsers(users.getContent());
                response.setPage_number((long) data.getPage());
                response.setSize_of_page((long) data.getSize());
                response.setTotal_page((long) users.getTotalPages());
                response.setTotal_count(users.getTotalElements());
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            }else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        }catch(Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;
    }
}
