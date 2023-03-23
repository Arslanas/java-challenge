package jp.co.axa.apidemo.repositories.impl;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Wrapper around JPA repository implementation.
 * Delegates actual communication with database to JPA repository.
 * It exposes only the methods used in the project instead of JPA implementation which contains a lot of unused methods.
 */
@Repository
public class EmployeeRepositoryJPAWrapper implements EmployeeRepository {

    private final EmployeeRepositoryJPA jpaRepository;

    public EmployeeRepositoryJPAWrapper(EmployeeRepositoryJPA jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Employee> getAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Employee> getById(long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public long create(Employee employee) {
        return jpaRepository.save(employee).getId();
    }

    @Override
    public void deleteById(long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void update(Employee employee) {
        jpaRepository.save(employee);
    }
}
