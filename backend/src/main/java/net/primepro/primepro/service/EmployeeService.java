package net.primepro.primepro.service;

import net.primepro.primepro.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);
    void deleteEmployee(Integer employeeId);
    Employee updateEmployee(Integer id, Employee updatedEmployee);
    List<Employee> viewAll();
    Employee getEmployee(Integer id);
}
