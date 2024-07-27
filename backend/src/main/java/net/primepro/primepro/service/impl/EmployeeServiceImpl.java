package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.mapper.EmployeeMapper;
import net.primepro.primepro.repository.EmployeeRepository;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public  EmployeeDto addEmployee(EmployeeDto employeeDto) {
         Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
//
          Employee savedemployee = employeeRepository.save(employee);


        return EmployeeMapper.mapToEmployeeDto(savedemployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public EmployeeDto editEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public List<Employee> viewAll() {
        return employeeRepository.findAll();
    }



}
