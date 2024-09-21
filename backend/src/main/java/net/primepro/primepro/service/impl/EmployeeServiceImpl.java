package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.repository.EmployeeRepository;
import net.primepro.primepro.repository.UsersRepo;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        // Check if the email already exists
        if (usersRepo.findByEmail(employeeDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        Employee employee = new Employee();
        employee.setEmail(employeeDto.getEmail());
        employee.setName(employeeDto.getName());
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        employee.setCity(employeeDto.getCity());
        employee.setRole(employeeDto.getRole());
        employee.setBranchName(employeeDto.getBranchName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setDesignation(employeeDto.getDesignation());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

//    @Override
//    public EmployeeDto editEmployee(EmployeeDto employeeDto) {
//        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
//    }

    @Override
    public List<Employee> viewAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(Long id){
//        log.info("Get {} product from database",id);
        return employeeRepository.findById(id).get();
    };
}
