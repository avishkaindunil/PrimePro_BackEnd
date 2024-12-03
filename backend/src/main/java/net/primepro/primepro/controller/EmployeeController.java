package net.primepro.primepro.controller;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.dto.UserEmployeeDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.OurUsers;
import net.primepro.primepro.exception.EmailAlreadyExistsException;
import net.primepro.primepro.service.EmployeeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
        try {
            Employee createdEmployee = employeeService.addEmployee(employee);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdEmployee.getEmployeeId())
                    .toUri();

            return ResponseEntity.created(location).body(createdEmployee);
        } catch (EmailAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmp(@PathVariable("id") Integer Id){
        employeeService.deleteEmployee(Id);
        return "Employee deleted";
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OurUsers> updateEmployee(@PathVariable Integer id, @RequestBody UserEmployeeDto employee){
        OurUsers updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/")
    public List<Employee> getAll(){
        return employeeService.viewAll();
    }

    @GetMapping("/getEmployee/{id}")
    public Employee getEmployee(@PathVariable Integer id){
        return employeeService.getEmployee(id);
    }

}
