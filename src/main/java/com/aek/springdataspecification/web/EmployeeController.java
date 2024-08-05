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

package com.aek.springdataspecification.web;

import com.aek.springdataspecification.entities.Employee;
import com.aek.springdataspecification.helpers.pagination.PageRequestBuilder;
import com.aek.springdataspecification.helpers.pagination.SearchFilters;
import com.aek.springdataspecification.helpers.pagination.SearchRequest;
import com.aek.springdataspecification.helpers.pagination.filter.FilterBuilder;
import com.aek.springdataspecification.helpers.pagination.filter.FilterSpecifications;
import com.aek.springdataspecification.helpers.response.PageResponse;
import com.aek.springdataspecification.service.EmployeeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;

/**
 * <h2>EmployeeController</h2>
 *
 * @author
 * createdAt : 2019-07-11 08:21
 * <p>
 * Description:
 */
@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping(value = "/employee/page")
    public ResponseEntity<PageResponse<?>> paginate(@RequestBody @Valid SearchRequest data) {

        PageResponse<Employee> resp = new PageResponse<>();
        Pageable pageable = PageRequestBuilder.getPageable(data.getSize(), data.getPage(), data.getOrderByFieldName(), data.getOrderDirection());

        SearchFilters searchFilters = new SearchFilters(data.getFilterAndConditions(),data.getFilterAndConditions());
        FilterSpecifications<Employee> specifications = new FilterSpecifications<>(searchFilters);

        var pg = employeeService.findAllSpecification(specifications, pageable);
        resp.setPageStats(pg, pg.getContent());
        return new ResponseEntity<>(
                resp,
                HttpStatus.OK);
    }

    @GetMapping(value = "/employee/search")
    public ResponseEntity<PageResponse<?>> search(
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "20") int size,
                                               @RequestParam(value = "or", required = false) String filterOr,
                                               @RequestParam(value = "and", required = false) String filterAnd) {

          PageResponse<Employee> resp = new PageResponse<>();
          Pageable pageable = PageRequestBuilder.getPageable(size, page, Collections.emptyList(), Sort.Direction.ASC);

        SearchFilters searchFilters = new SearchFilters(FilterBuilder.getFilters(filterAnd),FilterBuilder.getFilters(filterOr));
        FilterSpecifications<Employee> specifications = new FilterSpecifications<>(searchFilters);


        var pg = employeeService.findAllSpecification(specifications, pageable);
        resp.setPageStats(pg, pg.getContent());
        return new ResponseEntity<>(
                resp,
                HttpStatus.OK);

    }



}
