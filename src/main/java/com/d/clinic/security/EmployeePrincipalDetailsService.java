package com.d.clinic.security;

import com.d.clinic.entity.Employee;
import com.d.clinic.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeePrincipalDetailsService implements UserDetailsService{


    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee em = employeeRepository.findByUsername(username);

        if (em == null) {
            throw new UsernameNotFoundException("Error in UserName");
        } else {
            UserPrincipal userPrincipal = new UserPrincipal(em);

            return userPrincipal;
        }

    }
}
