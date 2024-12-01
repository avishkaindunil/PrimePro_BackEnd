package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.entity.Employee;
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
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
        usersRepo.deleteById(employeeId);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//        branch_name
//                date_of_birth
//        phone_number
//                designation
//        nic
//                no_of_annual_leaves
//        No_of_casual_leaves
//                No_of_medical_leaves
//        base_salary
//                is_probation
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setBranchName(updatedEmployee.getBranchName());
            existingEmployee.setBranchName(updatedEmployee.getBranchName());
            existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setDesignation(updatedEmployee.getDesignation());
            existingEmployee.setNic(updatedEmployee.getNic());
            existingEmployee.setNoOfCasualLeaves(updatedEmployee.getNoOfCasualLeaves());
            existingEmployee.setNoOfAnnualLeaves(updatedEmployee.getNoOfAnnualLeaves());
            existingEmployee.setNoOfMedicalLeaves(updatedEmployee.getNoOfMedicalLeaves());
            existingEmployee.setBaseSalary(updatedEmployee.getBaseSalary());
            existingEmployee.setProbation(updatedEmployee.isProbation());

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
    public Employee getEmployee(Integer id){
//        log.info("Get {} product from database",id);
        return employeeRepository.findById(id).get();
    };
}
