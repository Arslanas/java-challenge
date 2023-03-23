package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Employee repository interface used in the project instead of JPA repository directly
 */
public interface EmployeeRepository {

    List<Employee> getAll();

    Optional<Employee> getById(long id);

    long create(Employee employee);

    void deleteById(long id);

    void update(Employee employee);
}
