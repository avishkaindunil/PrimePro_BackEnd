package net.primepro.primepro.service;

import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(EmployeeDto employeeDto);
    void deleteEmployee(Long employeeId);
    Employee updateEmployee(Long id, Employee updatedEmployee);
    List<Employee> viewAll();
    Employee getEmployee(Long id);
}
