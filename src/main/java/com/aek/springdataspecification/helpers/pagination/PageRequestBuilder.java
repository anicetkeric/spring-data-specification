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

package com.aek.springdataspecification.helpers.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>PageRequestBuilder</h2>
 *
 * @author boottech
 * <p>
 * Description: Page Request Builder class. custom pageable
 */
public class PageRequestBuilder {

    private static final int DEFAULT_SIZE_PAGE = 20;

    private PageRequestBuilder() {
    }


    public static PageRequest getPageable(int size, int page, List<String> orderColumn, Sort.Direction sortDirection) {

        int pageSize = (size == 0) ? DEFAULT_SIZE_PAGE : size;
        int currentPage = (page <= 0) ? 1 : page;

        if (CollectionUtils.isEmpty(orderColumn)) {
            return PageRequest.of((currentPage - 1), pageSize);
        }

        if (sortDirection.equals(Sort.Direction.DESC) || sortDirection.equals(Sort.Direction.ASC)) {
            List<Sort.Order> orderList = new ArrayList<>();
            orderColumn.forEach(col -> orderList.add(new Sort.Order(sortDirection, col)));

            return PageRequest.of((currentPage - 1), pageSize, Sort.by(orderList));
        }

        throw new IllegalArgumentException(String.format("Value for param 'order' is not valid : %s , must be 'asc' or 'desc'", sortDirection));
    }
}
