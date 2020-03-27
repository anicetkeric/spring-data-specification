/*
 * Copyright (c) 2020. @aek - (anicetkeric@gmail.com)
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


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h2>FilterBuilder</h2>
 *
 * @author aek - macintoshhd
 * createdAt : 2020-03-25 18:52
 * <p>
 * Description:
 */
public class FilterBuilder {

    private FilterBuilder(){

    }

    private static List<String> split(String search, String delimiter) {
        return Stream.of(search.split(delimiter))
                //.map (elem -> new String(elem))
                .collect(Collectors.toList());
    }


    public static List<FilterCondition> getFilters(String searchCriteria) {

        List<FilterCondition> filters = new ArrayList<>();

        if (searchCriteria != null && !searchCriteria.isEmpty()) {

            List<String> values = split(searchCriteria, "&");
            if (!values.isEmpty()) {
                values.forEach(x -> {
                    List<String> filter = split(x, "\\|");

                    if(FilterOperation.fromValue(filter.get(1)) != null){
                        filters.add(new FilterCondition(filter.get(0), FilterOperation.fromValue(filter.get(1)), filter.get(2)));
                    }

                });
            }
        }

        return filters;

    }




}
