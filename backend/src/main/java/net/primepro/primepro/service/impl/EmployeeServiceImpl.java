package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.UserEmployeeDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.OurUsers;
import net.primepro.primepro.repository.EmployeeRepository;
import net.primepro.primepro.repository.UsersRepo;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        employee.getUser().setPassword(passwordEncoder.encode(employee.getUser().getPassword()));
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
        usersRepo.deleteById(employeeId);
    }

    @Override
    @Transactional
    public OurUsers updateEmployee(Integer id, UserEmployeeDto updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());

            Employee saveEmployee = employeeRepository.save(existingEmployee);

            Optional<OurUsers> optionalUser = usersRepo.findById(saveEmployee.getUser().getId());
            OurUsers existingUser = optionalUser.get();

            existingUser.setName(updatedEmployee.getName());
            existingUser.setProfilePictureUrl(updatedEmployee.getProfilePictureUrl());

            return usersRepo.save(existingUser);
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }

//    public Employee updateEmployee(Integer id, UserEmployeeDto updatedEmployee) {
//        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//        if (optionalEmployee.isPresent()) {
//            Employee existingEmployee = optionalEmployee.get();
//            existingEmployee.setBranchName(updatedEmployee.getBranchName());
//            existingEmployee.setBranchName(updatedEmployee.getBranchName());
//            existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
//            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
//            existingEmployee.setDesignation(updatedEmployee.getDesignation());
//            existingEmployee.setNic(updatedEmployee.getNic());
//            existingEmployee.setNoOfCasualLeaves(updatedEmployee.getNoOfCasualLeaves());
//            existingEmployee.setNoOfAnnualLeaves(updatedEmployee.getNoOfAnnualLeaves());
//            existingEmployee.setNoOfMedicalLeaves(updatedEmployee.getNoOfMedicalLeaves());
//            existingEmployee.setBaseSalary(updatedEmployee.getBaseSalary());
//            existingEmployee.setProbation(updatedEmployee.isProbation());
//
//            return employeeRepository.save(existingEmployee);  // Save updated employee
//        } else {
//            throw new RuntimeException("Employee not found with ID: " + id);
//        }
//    }

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
