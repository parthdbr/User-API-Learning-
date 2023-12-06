package com.swagger.swager.controller;

import com.swagger.swager.DTO.UserDTO;
import com.swagger.swager.Exception.UserExistsException;
import com.swagger.swager.decorator.*;
import com.swagger.swager.model.User;
import com.swagger.swager.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j

public class UserController {
    @Autowired
    private UserService userService;
     @Operation(summary = "Get users",
            description = "Get list of users",
            responses = {
            @ApiResponse(responseCode = "403", description = "Forbidden"/*, content =
                            { @Content(schema =
                            @Schema(implementation = AuthResponse.class)) }*/),
            @ApiResponse(responseCode = "401", description = "Not authorized for this operation"/*, content =
                    { @Content(schema =
                    @Schema(implementation = AuthResponse.class)) }*/),
            @ApiResponse(responseCode = "404", description = "No data Found!"),
            @ApiResponse(responseCode = "200", description = "Data Available"/*, content =
                    { @Content (schema =
                    @Schema(implementation = ListDataResponse.class))}*/ )})
    @GetMapping(value = "/userdata", produces = "application/json")
     @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<User> getAllUserData(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size )   {
        log.info("Inside Controller");
        Page<User> users = userService.getAllUserData(page, size);
        PageResponse<User> response = new PageResponse<>();
        try{

                if(!users.isEmpty()) {
                    response.setUsers(users.getContent());
                    response.setPage_number((long) page);
                    response.setSize_of_page((long) size);
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

    @Operation(summary = "Get user by ID",
            description = "Get user by ID",
            responses = {
                    @ApiResponse(responseCode = "401", description = "Not authorized for this operation"),
                    @ApiResponse(responseCode = "404", description = "No data Found!")})
    @GetMapping("/userdata/{id}")
    public  DataResponse<User> getUserDataById(@Parameter(description = "Data will be fetched by ID")@PathVariable("id") String id)   {
        User userdata = userService.getUserDataById(id);
        DataResponse<User> response = new DataResponse<>();
        try {
            if (userdata != null) {
                    response.setData(userdata);
                    response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            } else {
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
            }
        }catch(Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response ;
    }

//    @ApiIgnore
    @Operation(summary = "Create User",
            description = "Create a User",
            responses = {
                    @ApiResponse(responseCode = "401", description = "Not authorized for this operation")})
    @PostMapping("/userdata")
    public DataResponse<User> createUser(@Parameter(description = "Enter the user data")@RequestBody UserDTO userDTO) throws UserExistsException {
        DataResponse<User> response = new DataResponse<>();
        try {
                log.info("{}",userDTO);
                response.setData(userService.createUser(userDTO));
                response.setStatus(new Response(HttpStatus.CREATED, "Data Created", "201"));
        }catch(Exception e) {

            response.setStatus(new Response(HttpStatus.NO_CONTENT, "Data Not Created", "204"));
        }
        return response;
   }


    @Operation(summary = "Update user",
            description = "Update a user by ID",
            responses = {
                    @ApiResponse(responseCode = "401", description = "Not authorized for this operation"),
                    @ApiResponse(responseCode = "404", description = "No data Found!")})
    @PutMapping("/userdata/{id}")
    public DataResponse<User> updateUser(@Parameter(description = "The ID that needs to be updated")@PathVariable("id") String id,@Parameter(description = "Data to be updated based on ID") @RequestBody UserDTO userDTO) {
        DataResponse<User> response = new DataResponse<>();
        try {
            if (!ObjectUtils.isEmpty(userDTO)){
                response.setData(userService.updateUser(id, userDTO));
                response.setStatus(new Response(HttpStatus.ACCEPTED, "Data Modified", "202"));
            }else
                response.setStatus(new Response(HttpStatus.NOT_MODIFIED, "Data Not Modified", "304"));
        }catch(Exception e) {
            log.info("Some error updating user");
            response.setStatus(new Response(HttpStatus.NOT_MODIFIED, "Data Not Modified", "304"));
        }
        return response;
    }

    @Operation(summary = "Delete a user",
            description = "Delete a user by ID",
            responses = {
                    @ApiResponse(responseCode = "401", description = "Not authorized for this operation"),
                    @ApiResponse(responseCode = "404", description = "No data Found!")})
    @DeleteMapping("/userdata/{id}")
    public DataResponse<User> deleteUser( @Parameter(description = "The ID that needs to be deleted") @PathVariable("id") String id) {
        DataResponse<User> response = new DataResponse<>();
        try {
            if (userService.getUserDataById(id) != null) {
                userService.deleteUser(id);
                response.setStatus(new Response(HttpStatus.OK, "Data deleted", "200"));
            } else {
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "Data not found", "404"));
            }
        } catch (Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "Data not found", "404"));
        }
        return response;
    }

    @GetMapping(value = "/userdata/AgeSort", produces = "application/json")
    public ListDataResponse<User> findByAgeSorted() {

         ListDataResponse<User> response = new ListDataResponse<>();
        List<User> users = userService.findByAgeSorted();
        try{

            if(!users.isEmpty()) {
               response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            }else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        }catch(Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

/*    @GetMapping(value = "/userdata/findByGrade", produces = "application/json")
    public ListDataResponse<User> findByGrade(String grade) {

        ListDataResponse<User> response = new ListDataResponse<>();
        List<User> users = userService.findByGrade(grade);
        try{

            if(!users.isEmpty()) {
                response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            }else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        }catch(Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }*/

    @GetMapping(value = "/userdata/FirstNameSort", produces = "application/json")
    public ListDataResponse<User> findByFirstNameSort( @RequestParam String fname  ) {

        ListDataResponse<User> response = new ListDataResponse<>();
        List<User> users = userService.findByFirstNameSort(fname);
        try{

            if(!users.isEmpty()) {
                response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            }else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        }catch(Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

    @GetMapping(value = "/userdata/LastNameNameSort", produces = "application/json")
    public ListDataResponse<User> findByLastNameSort( @RequestParam String lname ) {

        ListDataResponse<User> response = new ListDataResponse<>();
        List<User> users = userService.findByLastNameSort(lname);
        try{

            if(!users.isEmpty()) {
                response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            }else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        }catch(Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

    @GetMapping(value = "/userdata/EmailSort", produces = "application/json")
    public ListDataResponse<User> findByEmailSort( ) {

        ListDataResponse<User> response = new ListDataResponse<>();
        List<User> users = userService.findByEmailSort();
        try{

            if(!users.isEmpty()) {
                response.setData(users);
                response.setStatus(new Response(HttpStatus.OK, "Data Fetched", "200"));
            }else
                response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));

        }catch(Exception e) {
            response.setStatus(new Response(HttpStatus.NOT_FOUND, "No Data Available", "404"));
        }
        return response;

    }

}
