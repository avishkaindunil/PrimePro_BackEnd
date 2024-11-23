package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.OurUsers;
import net.primepro.primepro.mapper.EmployeeMapper;
import net.primepro.primepro.repository.EmployeeRepository;
import net.primepro.primepro.repository.UsersRepo;
import net.primepro.primepro.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public  EmployeeDto addEmployee(EmployeeDto employeeDto) {
         Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
//
          Employee savedemployee = employeeRepository.save(employee);


        return EmployeeMapper.mapToEmployeeDto(savedemployee);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {

        employeeRepository.deleteById(employeeId);
        usersRepo.deleteById(employeeId);
    }

    @Override
    public EmployeeDto editEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        Optional<OurUsers> ourUserOptional = usersRepo.findById(savedEmployee.getId());
        if (ourUserOptional.isPresent()) {
            OurUsers ourUser = ourUserOptional.get();
            ourUser.setEmail(savedEmployee.getEmail());
            ourUser.setRole("EMPLOYEE");  // Assuming role is EMPLOYEE
            usersRepo.save(ourUser);
        }


        return EmployeeMapper.mapToEmployeeDto(savedEmployee);



    }

    @Override
    public List<Employee> viewAll() {
        return employeeRepository.findAll();
    }



}
