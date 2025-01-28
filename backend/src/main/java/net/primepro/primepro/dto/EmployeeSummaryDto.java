package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSummaryDto {
    private String employeeId;
    private String branchName;
    private String designation;
    private String phoneNumber;
    private Integer baseSalary;
    private Integer noOfAnnualLeaves;
    private Integer noOfCasualLeaves;
    private Integer noOfMedicalLeaves;
}
