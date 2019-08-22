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

package com.aek.springdataspecification.web;

import com.aek.springdataspecification.entities.Employee;
import com.aek.springdataspecification.helpers.pagination.PageRequestBuilder;
import com.aek.springdataspecification.helpers.pagination.SearchRequest;
import com.aek.springdataspecification.helpers.pagination.filter.FilterSpecifications;
import com.aek.springdataspecification.helpers.response.PageResponse;
import com.aek.springdataspecification.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <h2>EmployeeController</h2>
 *
 * @author aek - macintoshhd
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
    public ResponseEntity<PageResponse> paginate(@RequestBody @Valid SearchRequest data) {


        FilterSpecifications<Employee> c = new FilterSpecifications<>();
        Page<Employee> pg;
        PageResponse<Employee> resp = new PageResponse<>();
        Pageable pageable = PageRequestBuilder.getPageable(data.getSize(), data.getPage(), data.getOrderByFieldName(), data.getOrderDirection());

        c.addCondition(data.getFilterAndConditions(),data.getFilterOrConditions());

        pg = employeeService.findAllSpecification(c, pageable);
        resp.setPageStats(pg,pg.getContent());
        return new ResponseEntity<>(
                resp,
                HttpStatus.OK);


    }

}
