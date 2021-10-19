package com.demo;

import com.demo.base.BaseResponse;
import com.demo.company.controller.EmployeeControllerPath;
import com.demo.company.entity.Department;
import com.demo.company.entity.Employee;
import com.demo.company.repository.EmployeeRepository;
import com.demo.dto.DepartmentRequest;
import com.demo.dto.EmployeeCreateRequest;
import com.demo.dto.EmployeeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class EmployeeControllerIntegrationTest {

    public static final Integer EMP_NO_1 = 1;
    public static final String EMP_NAME_1 = "humangmail";
    public static final String JOB_1 = "human2@gmail.com";
    public static final Integer MGR_1 = 1;
    public static final String HIRE_DATE_1 = "human2@gmail.com";
    public static final Double SAL_1 = 1_000_000d;
    public static final Double COMM_1 = 1_000_000d;
    public static final DepartmentRequest DEPARTMENT_1 = DepartmentRequest.builder()
            .deptNo(1)
            .deptName("Customer Service")
            .loc("Street A")
            .build();

    private EmployeeCreateRequest employeeCreateRequest;

    @LocalServerPort
    private int port;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void init() throws Exception {
        RestAssured.port = port;
        employeeRepository.deleteAll();
    }

    

}
