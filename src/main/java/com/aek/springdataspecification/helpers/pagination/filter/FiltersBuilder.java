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

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h2>FiltersBuilder</h2>
 *
 * @author aek - macintoshhd
 * createdAt : 2020-03-25 18:52
 * <p>
 * Description:
 */
public class FiltersBuilder {

    private FiltersBuilder(){

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
                    List<String> fil = split(x, "\\|");
                    filters.add(new FilterCondition(fil.get(0), FilterOperation.fromValue(fil.get(1)), fil.get(2)));

                });


            }
        }

        return filters;

    }

    public static PageRequest getPageable(int size, int page, List<String> orderColumn, Sort.Direction sortDirection) {


        int pageSize = (size == 0) ? 20 : size;
        int curentPage = (page <= 0) ? 1 : page;

        if (orderColumn.isEmpty()) {
            return PageRequest.of((curentPage - 1), pageSize);
        } else {
            if (sortDirection.equals(Sort.Direction.DESC) || sortDirection.equals(Sort.Direction.ASC)) {

                Sort sort = new Sort(sortDirection, orderColumn);
                return PageRequest.of((curentPage - 1), pageSize, sort);
            } else {
                throw new IllegalArgumentException(String.format("Value for param 'order' is not valid : %s , must be 'asc' or 'desc'", sortDirection));
            }

        }

    }

}
