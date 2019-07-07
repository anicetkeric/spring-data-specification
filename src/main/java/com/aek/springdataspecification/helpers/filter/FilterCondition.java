package com.aek.springdataspecification.helpers.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class FilterCondition {

    private FilterOperation operator; //filter operator in list enum

    private Object value; //value for search

    private String field; //property for search


}
