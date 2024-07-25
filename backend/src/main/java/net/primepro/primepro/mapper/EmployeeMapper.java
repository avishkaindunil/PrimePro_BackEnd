package net.primepro.primepro.mapper;

import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee){


        return new EmployeeDto(
                employee.getId(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getEmail()
        );


    }


    public static Employee mapToEmployee(EmployeeDto employeeDto){


        return new Employee(
               employeeDto.getId(),
                employeeDto.getUsername(),
                employeeDto.getPassword(),
                employeeDto.getEmail()

        );
    }

}
