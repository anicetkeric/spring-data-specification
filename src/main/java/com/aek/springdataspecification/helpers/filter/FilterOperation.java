package com.aek.springdataspecification.helpers.filter;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <h2>FilterOperation</h2>
 *
 * @author macintoshhd
 * createdAt : 2019-07-06 09:22
 * <p>
 * Description:
 */
public enum FilterOperation {

    EQUAL						("eq"),
    NOT_EQUAL					("neq"),
    GREATER_THAN				("gt"),
    GREATER_THAN_OR_EQUAL_TO	("gte"),
    LESS_THAN					("lt"),
    LESSTHAN_OR_EQUAL_TO		("lte"),
    IN							("in"),
    NOT_IN						("nin"),
    BETWEEN						("btn"),
    CONTAINS					("like"),
    NOT_CONTAINS					("notLike"),
    IS_NULL					("isnull"),
    IS_NOT_NULL					("isnotnull"),
    START_WITH					("startwith"),
    END_WITH					("endwith"),
    IS_EMPTY					("isempty"),
    IS_NOT_EMPTY				("isnotempty"),
    JOIN					("jn");


    private String value;

    FilterOperation(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    public static FilterOperation fromValue(String value) {
        for (FilterOperation op : FilterOperation.values()) {

            //Case insensitive operation name
            if (String.valueOf(op.value).equalsIgnoreCase(value)) {
                return op;
            }
        }
        return null;
    }

}
