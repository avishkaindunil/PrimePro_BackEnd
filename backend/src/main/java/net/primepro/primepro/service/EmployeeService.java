package net.primepro.primepro.service;

import net.primepro.primepro.dto.UserEmployeeDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.OurUsers;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);
    void deleteEmployee(Integer employeeId);
    OurUsers updateEmployee(Integer id, UserEmployeeDto updatedEmployee);
    List<Employee> viewAll();
    Employee getEmployee(Integer id);
}
