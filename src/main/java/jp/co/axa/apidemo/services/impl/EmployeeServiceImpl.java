package jp.co.axa.apidemo.services.impl;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to handle employee data.
 * Cache is used to avoid unnecessary database calls. Currently uses default ConcurrentMapCache. It is fine as simple solution for single instance.
 * But if application will be running as a cluster of multiple instances then consider switching to Redis or Memcache distributed cache.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll() {
        return repository.getAll();
    }

    @Cacheable("employee")
    public Optional<Employee> getById(long id) {
        return repository.getById(id);
    }

    @CacheEvict(value = "employee", key = "#result")
    public long create(Employee employee){
        return repository.create(employee);
    }

    @CacheEvict("employee")
    public void deleteById(long id){
        repository.deleteById(id);
    }

    @CacheEvict(value = "employee", key = "#employee.id")
    public void update(Employee employee) {
        repository.update(employee);
    }
}