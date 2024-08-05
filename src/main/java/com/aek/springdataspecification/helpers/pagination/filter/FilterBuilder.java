/*
 * Copyright (c) 2020. @boottech - (boottechnologies@hotmail.com)
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


import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * <h2>FilterBuilder</h2>
 *
 * @author
 * createdAt : 2020-03-25 18:52
 * <p>
 * Description:
 */
@UtilityClass
public class FilterBuilder {


    private List<String> split(String search, String delimiter) {
        return Stream.of(search.split(delimiter)).toList();
    }


    public List<FilterCondition> getFilters(String searchCriteria) {

        List<FilterCondition> filters = new ArrayList<>();

         if (StringUtils.hasLength(searchCriteria)) {

            List<String> values = split(searchCriteria, ",");
             if (!CollectionUtils.isEmpty(values)) {
                values.forEach(x -> {
                    List<String> filter = split(x, ";");

                    var operator = FilterOperation.fromValue(filter.get(1));
                    if (Objects.nonNull(operator)) {
                        filters.add(new FilterCondition(filter.get(0), operator, filter.get(2)));
                    }

                });
            }
        }

        return filters;

    }




}
