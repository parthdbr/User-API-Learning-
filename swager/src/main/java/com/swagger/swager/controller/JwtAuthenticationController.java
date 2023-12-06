package com.swagger.swager.controller;

import com.swagger.swager.DTO.LoginDTO;
import com.swagger.swager.Util.JwtUtil;
import com.swagger.swager.decorator.AuthResponse;
import com.swagger.swager.decorator.Response;
import com.swagger.swager.model.User;
import com.swagger.swager.repository.UserRepository;
import com.swagger.swager.service.JwtUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@Slf4j
public class JwtAuthenticationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailService userDetailService;

    @PostMapping(value = "/login",  produces = "application/json")
    public AuthResponse loginUser(@RequestBody LoginDTO loginDTO) {

        AuthResponse AuthResponse = new AuthResponse();


        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

            User user = userRepository.findByEmailContainingAndSoftDeleteIsFalse(loginDTO.getUsername());

            SecurityContextHolder.getContext().setAuthentication(auth);

            if (auth.isAuthenticated()) {
                UserDetails userDetails = userDetailService.loadUserByUsername(loginDTO.getUsername());
                String token = jwtUtil.generateToken(auth);

                Map<String, Object> data = new HashMap<>();
                data.put("accessToken", token);

                AuthResponse.setStatus(new Response(HttpStatus.OK, data.get("accessToken").toString(), "200"));

            } else {

                AuthResponse.setStatus(new Response(HttpStatus.UNAUTHORIZED, "User login failed", "401"));
            }



        }catch(Exception e){
            AuthResponse.setStatus(new Response(HttpStatus.UNAUTHORIZED, "User login failed", "401"));
        }



        return AuthResponse;

    }



}
