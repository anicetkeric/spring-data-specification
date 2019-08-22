/*
 * Copyright (c) 2019. @aek - (anicetkeric@gmail.com)
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

import java.util.List;

/**
 * <h2>PageRequestBuilder</h2>
 *
 * @author aek - macintoshhd
 * createdAt : 2019-07-06 16:19
 * <p>
 * Description: Page Request Builder class. custom pageable
 */
public class PageRequestBuilder {

    private static final int DEFAULT_SIZE_PAGE = 20;

    private PageRequestBuilder() {
    }


    public static PageRequest getPageable(int size, int page, List<String> orderColumn, Sort.Direction sortDirection) {


        int pageSize = (size == 0) ? DEFAULT_SIZE_PAGE : size;
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
