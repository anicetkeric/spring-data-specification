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
