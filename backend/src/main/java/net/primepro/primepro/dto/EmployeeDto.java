package net.primepro.primepro.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class EmployeeDto {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String centerID;
    private String address;
    private String phoneNo;
    private Integer No_of_leaves;
    private Integer No_of_workdays;



    public EmployeeDto(Integer id, String username, String email, String centerID, Integer noOfWorkdays, Integer noOfLeaves, String address, String phoneNo) {
    }

    public EmployeeDto() {
    }
    
    public String getCenterID() {
        return centerID;
    }

    public void setCenterID(String centerID) {
        this.centerID = centerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getNo_of_leaves() {
        return No_of_leaves;
    }

    public void setNo_of_leaves(Integer no_of_leaves) {
        No_of_leaves = no_of_leaves;
    }

    public Integer getNo_of_workdays() {
        return No_of_workdays;
    }

    public void setNo_of_workdays(Integer no_of_workdays) {
        No_of_workdays = no_of_workdays;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


