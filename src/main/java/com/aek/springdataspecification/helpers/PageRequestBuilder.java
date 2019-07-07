package com.aek.springdataspecification.helpers;

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


    private PageRequestBuilder() {
    }
    private static final int DEFAULT_PAGE = 20;

    public static PageRequest getPageable(int size, int page, List<String> orderColumn, Sort.Direction sortDirection) {


        int pageSize = (size == 0) ? DEFAULT_PAGE : size;
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
