package com.aek.springdataspecification.service;

import com.aek.springdataspecification.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * <h2>EmployeeService</h2>
 *
 * @author aek - macintoshhd
 * createdAt : 2019-07-11 08:20
 * <p>
 * Description: Employee Service Class
 */
public interface EmployeeService {

    Page<Employee> findAllSpecification(Specification<Employee> specs, Pageable pageable);
    Page<Employee> findAllPaginate(Pageable pageable);
}
