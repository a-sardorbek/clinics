package com.d.clinic.entity;

import com.d.clinic.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeInit implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        this.employeeRepository.deleteAll();

        Employee e1 = new Employee("admin",passwordEncoder.encode("admin123"),"RECEPTION");

        List<Employee> users = Arrays.asList(e1);

        this.employeeRepository.saveAll(users);


    }
}
