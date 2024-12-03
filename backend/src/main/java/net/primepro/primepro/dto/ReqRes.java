package net.primepro.primepro.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import net.primepro.primepro.constants.UserTypesEnum;
import net.primepro.primepro.entity.OurUsers;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String city;
    private String role;
    private String email;
    private String password;
    private String profilePictureUrl;
    private boolean isUserActivated;

    private Integer userId;
    private Integer employeeId;
    private String employeeNumber;
    private String branchName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String designation;
    private String nic;
    private Integer noOfAnnualLeaves;
    private Integer noOfCasualLeaves;
    private Integer noOfMedicalLeaves;
    private Integer baseSalary;
    private boolean isProbation;

    private OurUsers ourUsers;
    private List<OurUsers> ourUsersList;

}