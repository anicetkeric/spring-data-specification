package com.aek.springdataspecification.helpers.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * <h2>FilterCondition</h2>
 *
 * @author macintoshhd
 * createdAt : 2019-07-06 09:17
 * <p>
 * Description: Filter Condition Class
 */
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class FilterCondition {

    private String field;
    private FilterOperation operator;
    private Object value;

}
