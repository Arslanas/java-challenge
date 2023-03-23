package jp.co.axa.apidemo.repositories.impl;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositoryJPA extends JpaRepository<Employee,Long> {
}
