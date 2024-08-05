# spring-data-specification
Spring Data JPA – Dynamically build queries using JPA Criteria API/Specification



## Prerequisites
Ensure you have this installed before proceeding further

* Spring Boot 3.3.2
* Lombok
* Java 21
* H2 database
* Spring Tool Suite™ (STS) or others

### Features

* Order By direction and order By ColumnsName
* Equal/NotEqual/Like/NotLike/In/NotIn/Join support multiple values, Equal/NotEqual support **Null** value.
* Support custom specification.
* Custom Pagination response

Department ---> Employee

Create a base Repository class should extends from two super class **JpaRepository** and **JpaSpecificationExecutor**.

```java
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> , JpaSpecificationExecutor<T> {

} 
```

Implement specification in Service class
```java
@Service
public class EmployeeServiceImpl implements EmployeeService {

  
   private final EmployeeRepository employeeRepository;
    
   public EmployeeServiceImpl(EmployeeRepository employeeRepo){
    this.employeeRepository = employeeRepo;
   }

    @Override
    public Page<Employee> findAllSpecification(Specification<Employee> specs, Pageable pageable) {
        return employeeRepository.findAll(specs, pageable);
    }

}
```
Endpoint demo
```
GET http://localhost:8080/api/employee/search?page=1&size=10&and=firstName;eq;Steven
GET http://localhost:8080/api/employee/search?page=1&size=10&or=firstName;startwith;K,firstName;like;ir
GET http://localhost:8080/api/employee/search?page=1&size=10&or=firstName;startwith;K,firstName;like;on
```



