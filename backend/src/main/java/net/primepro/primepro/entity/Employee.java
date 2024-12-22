package net.primepro.primepro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "designation")
    private String designation;

    @Column(name = "nic")
    private String nic;

    @Column(name="no_of_annual_leaves")
    private Integer noOfAnnualLeaves;

    @Column(name="no_of_casual_leaves")
    private Integer noOfCasualLeaves;

    @Column(name="no_of_medical_leaves")
    private Integer noOfMedicalLeaves;

    @Column(name="base_salary")
    private Integer baseSalary;

    @Column(name = "is_probation")
    private boolean isProbation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private OurUsers user;

    @PrePersist
    public void generateEmployeeId() {
        this.employeeId = "EMP-" + UUID.randomUUID().toString();
    }

}


