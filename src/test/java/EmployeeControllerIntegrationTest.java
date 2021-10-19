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

    @Test
    public void createEmployee_success_returnBaseResponse() throws Exception {
        /* Success */
        Assert.assertNotNull("baseResponse.getRequestId()");


        // Call register API
        employeeCreateRequest = EmployeeCreateRequest.builder()
                .empNo(EMP_NO_1)
                .empName(EMP_NAME_1)
                .job(JOB_1)
                .mgr(MGR_1)
                .hireDate(HIRE_DATE_1)
                .sal(SAL_1)
                .comm(COMM_1)
                .department(DEPARTMENT_1)
                .build();
//        ValidatableResponse validatableResponse =
//                RestAssured.given().contentType(ContentType.JSON).body(employeeCreateRequest)
//                        .queryParam("storeId", "aaaaa")
//                        .queryParam("channelId", "aaaaa")
//                        .queryParam("clientId", "aaaaa")
//                        .queryParam("requestId", "aaaaa")
//                        .queryParam("username", "aaaaa")
//                        .post("/demo" + EmployeeControllerPath.BASE_PATH).then();
//        System.out.println(validatableResponse.extract().asString());
        Employee employee = Optional.ofNullable(employeeCreateRequest).map(e -> {
            Employee employeex = Employee.builder().build();
            BeanUtils.copyProperties(e, employeex);

            Department department = Optional.ofNullable(employeex.getDepartment()).map(d -> {
                Department departmentx = Department.builder().build();
                BeanUtils.copyProperties(d, departmentx);
                return departmentx;
            }).orElse(null);

            employeex.setDepartment(department);
            return employeex;
        }).orElse(null);
        employeeRepository.save(employee);

        System.out.println(employeeRepository.findAll());

    }

}
