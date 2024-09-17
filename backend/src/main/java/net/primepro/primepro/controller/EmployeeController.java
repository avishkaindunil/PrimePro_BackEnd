package net.primepro.primepro.controller;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.service.EmployeeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public boolean createEmployee(@RequestBody EmployeeDto employeeDto){
          employeeService.addEmployee(employeeDto);
          System.out.println("Employee Added");
          return true;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmp(@PathVariable("id") Long Id){
        employeeService.deleteEmployee(Id);
        return "Employee deleted";
    }

//    @PostMapping("/edit")
//    public String editEmp(@RequestBody EmployeeDto employeeDto){
//        employeeService.editEmployee(employeeDto);
//        return "Employee edited";
//    }

    @GetMapping("/getEmployees")
    public List<Employee> getAll(){
        return  employeeService.viewAll();
    }

    @GetMapping("/getEmployee/{id}")
    public Employee getEmployee(@PathVariable Long id){
        return employeeService.getEmployee(id);
    }

}
