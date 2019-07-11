package com.aek.springdataspecification.service;

import com.aek.springdataspecification.entities.Employee;
import com.aek.springdataspecification.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <h2>EmployeeServiceImpl</h2>
 *
 * @author aek - macintoshhd
 * createdAt : 2019-07-11 08:21
 * <p>
 * Description:
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Page<Employee> findAllSpecification(Specification<Employee> specs, Pageable pageable) {
        return employeeRepository.findAll(specs, pageable);
    }

    public Page<Employee> findAllPaginate(Pageable pageable) {

        return employeeRepository.findAll(pageable);
    }
}
