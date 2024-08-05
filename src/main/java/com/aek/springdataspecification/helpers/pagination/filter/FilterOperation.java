/*
 * Copyright (c) 2019. @boottech - (boottechnologies@hotmail.com)
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.aek.springdataspecification.helpers.pagination.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

/**
 * <h2>FilterOperation</h2>
 *
 * @author boottech
 * <p>
 * Description:
 */
@Getter
@AllArgsConstructor
public enum FilterOperation {

    EQUAL("eq"),
    NOT_EQUAL("neq"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL_TO("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL_TO("lte"),
    IN("in"),
    NOT_IN("nin"),
    BETWEEN("btn"),
    CONTAINS("like"),
    NOT_CONTAINS("notLike"),
    IS_NULL("isnull"),
    IS_NOT_NULL("isnotnull"),
    START_WITH("startwith"),
    END_WITH("endwith"),
    IS_EMPTY("isempty"),
    IS_NOT_EMPTY("isnotempty"),
    JOIN("jn");

    private final String value;

    public static FilterOperation fromValue(@NotNull String value) {
        return Arrays.stream(FilterOperation.values())
                .filter(val -> val.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
