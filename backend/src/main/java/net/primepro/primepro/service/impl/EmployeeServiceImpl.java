package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.exception.EmailAlreadyExistsException;
import net.primepro.primepro.repository.EmployeeRepository;
import net.primepro.primepro.repository.UsersRepo;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            throw new EmailAlreadyExistsException("Email already in use");
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

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setPassword(updatedEmployee.getPassword());
            existingEmployee.setCity(updatedEmployee.getCity());
            existingEmployee.setRole(updatedEmployee.getRole());
            existingEmployee.setProfilePictureUrl(updatedEmployee.getProfilePictureUrl());
            existingEmployee.setUserActivated(updatedEmployee.isUserActivated());
            existingEmployee.setBranchName(updatedEmployee.getBranchName());
            existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setDesignation(updatedEmployee.getDesignation());

            return employeeRepository.save(existingEmployee);  // Save updated employee
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }

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
