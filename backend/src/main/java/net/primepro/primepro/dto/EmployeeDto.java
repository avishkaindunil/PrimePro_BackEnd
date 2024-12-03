package net.primepro.primepro.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.primepro.primepro.constants.UserTypesEnum;

import java.util.Date;

@Getter
@Setter

public class EmployeeDto {
    private int id;
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


