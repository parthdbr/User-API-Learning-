package com.swagger.swager.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swagger.swager.decorator.Response;
import com.swagger.swager.decorator.Unauthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        Unauthorized ua = new Unauthorized();

        response.setContentType("application/JSON");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ua.setStatus(new Response(HttpStatus.UNAUTHORIZED, "Unauthorized", "401"));
        response.getWriter().write(mapper.writeValueAsString(ua));
    }
}
