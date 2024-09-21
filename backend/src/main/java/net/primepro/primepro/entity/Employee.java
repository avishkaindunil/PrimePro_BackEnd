package net.primepro.primepro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@Data
public class Employee extends OurUsers {
    private String employeeId;
    private String branchName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String designation;

    @PrePersist
    public void generateEmployeeId() {
        this.employeeId = "EMP-" + UUID.randomUUID().toString();
    }
}


