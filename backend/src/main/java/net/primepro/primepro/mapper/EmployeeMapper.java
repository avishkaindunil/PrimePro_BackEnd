package net.primepro.primepro.mapper;

import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee){


        return new EmployeeDto(
                employee.getId(),
                employee.getUsername(),
                employee.getEmail(),
                employee.getCenterID(),
                employee.getNo_of_workdays(),
                employee.getNo_of_leaves(),
                employee.getAddress(),
                employee.getPhoneNo()
        );


    }


    public static Employee mapToEmployee(EmployeeDto employeeDto){


        return new Employee(
                employeeDto.getId(),
                employeeDto.getUsername(),
                employeeDto.getEmail(),
                employeeDto.getCenterID(),
                employeeDto.getAddress(),
                employeeDto.getNo_of_workdays(),
                employeeDto.getNo_of_leaves(),
                employeeDto.getPassword(),
                employeeDto.getPhoneNo()

        );
    }

}
