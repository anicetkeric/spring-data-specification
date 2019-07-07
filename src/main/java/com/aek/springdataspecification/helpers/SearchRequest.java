package com.aek.springdataspecification.helpers;

import com.aek.springdataspecification.helpers.filter.FilterCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * <h2>SearchRequest</h2>
 *
 * @author macintoshhd
 * createdAt : 2019-07-06 09:11
 * <p>
 * Description: Class Pagination
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {

    private int size = 20;
    private int page = 1;

    private Sort.Direction orderDirection;
    private List<String> orderByFieldName;
    private List<FilterCondition> filterColumn;


}
