package com.swagger.swager.decorator;

import com.swagger.swager.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unauthorized {
    User data;
    Response status;

}
