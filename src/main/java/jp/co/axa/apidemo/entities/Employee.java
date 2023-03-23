package jp.co.axa.apidemo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="EMPLOYEE")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME")
    @NotBlank
    @Size(max = 50, message = "Maximum length is 50")
    private String name;

    @Column(name="SALARY")
    @NotNull
    private Integer salary;

    @Column(name="DEPARTMENT")
    @NotBlank
    @Size(max = 30, message = "Maximum length is 30")
    private String department;

}
