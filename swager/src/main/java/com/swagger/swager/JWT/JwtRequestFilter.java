package com.swagger.swager.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swagger.swager.Util.JwtUtil;
import com.swagger.swager.decorator.Response;
import com.swagger.swager.decorator.Unauthorized;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;

        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ") ){
            jwtToken = requestTokenHeader.substring(7);
            try{
                username = jwtUtil.getUsernameFromToken(jwtToken);
            }catch(IllegalArgumentException e){
                System.out.println("Unable to get JWT Token ");
            }catch(ExpiredJwtException e){
                System.out.println("JWT Token has Expired ");
            }
        }else{
//            ObjectMapper mapper = new ObjectMapper();
//            Unauthorized ua = new Unauthorized();
//
//            response.setContentType("application/JSON");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            ua.setStatus(new Response(HttpStatus.UNAUTHORIZED, "Unauthorized", "401"));
//            response.getWriter().write(mapper.writeValueAsString(ua));

            logger.warn("JWT Token does not Starts with Bearer ");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtUtil.getAuthenticationToken(
                        jwtToken, SecurityContextHolder.getContext().getAuthentication(), userDetails
                );

                        /*new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );*/

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }
        filterChain.doFilter(request,response);
    }
    }

