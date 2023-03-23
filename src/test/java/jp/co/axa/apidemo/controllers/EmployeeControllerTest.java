package jp.co.axa.apidemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository repository;


    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/ResetEmployee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/Employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void saveEmployee() throws Exception {
        String requestBody = "{ \"name\": \"Test User\", \"salary\": 100, \"department\": \"IT\" }";

        // create entity request
        MvcResult result = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        ).andExpect(status().isCreated()).andReturn();
        long generatedId = Long.parseLong(result.getResponse().getContentAsString());

        // verify created entity data
        Optional<Employee> employeeFromDB = repository.getById(generatedId);
        assertTrue(employeeFromDB.isPresent());
        Employee employee = employeeFromDB.get();
        assertEquals(generatedId, 4L);
        assertEquals((long)employee.getId(), 4L);
        assertEquals(employee.getDepartment(), "IT");
        assertEquals(employee.getName(), "Test User");
        assertEquals((long) employee.getSalary(), 100L);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/ResetEmployee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/Employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void getEmployees() throws Exception {
        // read and verify entity list data
        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("John Doe"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("Alice Van"))
                .andExpect(jsonPath("$.[2].id").value(3))
                .andExpect(jsonPath("$.[2].name").value("Steve Richards"));
        assertEquals(repository.getAll().size(), 3);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/ResetEmployee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/Employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void getEmployee() throws Exception {
        // read and verify entity data
        mockMvc.perform(get("/api/v1/employees/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Alice Van"))
                .andExpect(jsonPath("$.salary").value(150))
                .andExpect(jsonPath("$.department").value("IT"));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/ResetEmployee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/Employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void deleteEmployee() throws Exception {
        //delete entity
        mockMvc.perform(delete("/api/v1/employees/2"))
                .andExpect(status().isNoContent());

        // verify entity no longer exists
        mockMvc.perform(get("/api/v1/employees/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("ENTITY_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Resource was not found by id [2]"))
        ;
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:sql/ResetEmployee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:sql/Employee.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void updateEmployee() throws Exception {
        // update entity
        String requestBody = "{ \"name\": \"Test User\", \"salary\": 400, \"department\": \"IT\" }";
        mockMvc.perform(put("/api/v1/employees/2")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(requestBody)
                ).andExpect(status().isOk());

        // verify entity was updated
        mockMvc.perform(get("/api/v1/employees/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.salary").value(400))
        ;
    }
}