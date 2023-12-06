package com.swagger.swager.decorator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class getUserBySearchandField {
    String search;
    String field;
    String order;
    int page;
    int size;
}
