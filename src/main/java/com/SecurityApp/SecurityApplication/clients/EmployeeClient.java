package com.SecurityApp.SecurityApplication.clients;

import com.SecurityApp.SecurityApplication.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {
    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long employeeId);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
}
