package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.EntityNotFound;
import jp.co.axa.apidemo.exception.ErrorInfo;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(value = "Read all employees")
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.getAll();
    }

    @ApiOperation(value = "Read employee by id")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Employee not found", response = ErrorInfo.class)
    })
    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable(name = "id") long id) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return employeeService.getById(id).orElseThrow(() -> new EntityNotFound(id));
    }

    @ApiOperation(value = "Create new employee and get generated id", notes = "Returns generated ID")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public long saveEmployee(@RequestBody @Valid Employee employee) {
        long generatedId = employeeService.create(employee);
        log.debug("Employee [{}] saved successfully. Generated id [{}] ", employee, generatedId);
        return generatedId;
    }

    @ApiOperation(value = "Delete employee by id")
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable(name = "id") long id) {
        employeeService.deleteById(id);
        log.debug("Employee {} deleted successfully", id);
    }

    @ApiOperation(value = "Update employee data by id", notes = "ID of request body will be ignored, instead uses ID from URL path")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployee(@RequestBody @Valid Employee employee,
                               @PathVariable(name = "id") long id) {
        employee.setId(id);
        employeeService.update(employee);
        log.debug("Employee updated successfully by id {}", id);
    }

}
