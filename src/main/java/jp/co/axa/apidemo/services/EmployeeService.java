package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAll();

    Optional<Employee> getById(long id);

    long create(Employee employee);

    void deleteById(long id);

    void update(Employee employee);
}