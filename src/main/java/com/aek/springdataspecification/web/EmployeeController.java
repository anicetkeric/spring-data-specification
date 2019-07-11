package com.aek.springdataspecification.web;

import com.aek.springdataspecification.entities.Employee;
import com.aek.springdataspecification.helpers.PageRequestBuilder;
import com.aek.springdataspecification.helpers.PageResponse;
import com.aek.springdataspecification.helpers.SearchRequest;
import com.aek.springdataspecification.helpers.filter.FilterSpecifications;
import com.aek.springdataspecification.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    EmployeeService employeeService;


    @PostMapping(value = "/employee/page")
    public ResponseEntity<PageResponse> paginate(@RequestBody @Valid SearchRequest data) {

        FilterSpecifications<Employee> c = new FilterSpecifications<>();
        Page<Employee> pg;
        PageResponse<Employee> resp = new PageResponse<>();
        Pageable pageable = PageRequestBuilder.getPageable(data.getSize(), data.getPage(), data.getOrderByFieldName(), data.getOrderDirection());

        if (!data.getFilterColumn().isEmpty()) {
            c.addCondition(data.getFilterColumn());
            pg = employeeService.findAllSpecification(c, pageable);
        }else{
            pg = employeeService.findAllPaginate(pageable);
        }
        resp.setPageStats(pg,pg.getContent());
        return new ResponseEntity<>(
                resp,
                HttpStatus.OK);

    }

}
