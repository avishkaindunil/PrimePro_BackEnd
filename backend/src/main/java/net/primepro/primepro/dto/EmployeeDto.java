package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.primepro.primepro.constants.UserTypesEnum;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDto {
//    private Long id;
    private String email;
    private String name;
    private String password;
    private String city;
    private UserTypesEnum role;
    private String profilePictureUrl;
    private boolean isUserActivated;
    private String employeeId;
    private String branchName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String designation;
}


