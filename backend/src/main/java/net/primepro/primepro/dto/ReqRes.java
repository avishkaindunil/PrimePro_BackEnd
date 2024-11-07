package net.primepro.primepro.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.primepro.primepro.entity.OurUsers;
import lombok.Data;

import java.util.List;

//@Getter
//@Setter
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
    private String username;
    private String city;
    private String role;
    private String email;
    private String password;
    private String centerID;
    private String address;
    private String phoneNo;
    private Integer No_of_leaves;
    private Integer No_of_workdays;
    private OurUsers ourUsers;
    private List<OurUsers> ourUsersList;

//    public void setStatusCode(int statusCode) {
//        this.statusCode = statusCode;
//    }
//
//    public String getError() {
//        return error;
//    }
//
//    public void setError(String error) {
//        this.error = error;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getRefreshToken() {
//        return refreshToken;
//    }
//
//    public void setRefreshToken(String refreshToken) {
//        this.refreshToken = refreshToken;
//    }
//
//    public String getExpirationTime() {
//        return expirationTime;
//    }
//
//    public void setExpirationTime(String expirationTime) {
//        this.expirationTime = expirationTime;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getCenterID() {
//        return centerID;
//    }
//
//    public void setCenterID(String centerID) {
//        this.centerID = centerID;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPhoneNo() {
//        return phoneNo;
//    }
//
//    public void setPhoneNo(String phoneNo) {
//        this.phoneNo = phoneNo;
//    }
//
//    public Integer getNo_of_leaves() {
//        return No_of_leaves;
//    }
//
//    public void setNo_of_leaves(Integer no_of_leaves) {
//        No_of_leaves = no_of_leaves;
//    }
//
//    public Integer getNo_of_workdays() {
//        return No_of_workdays;
//    }
//
//    public void setNo_of_workdays(Integer no_of_workdays) {
//        No_of_workdays = no_of_workdays;
//    }
//
//    public OurUsers getOurUsers() {
//        return ourUsers;
//    }
//
//    public void setOurUsers(OurUsers ourUsers) {
//        this.ourUsers = ourUsers;
//    }
//
//    public List<OurUsers> getOurUsersList() {
//        return ourUsersList;
//    }
//
//    public void setOurUsersList(List<OurUsers> ourUsersList) {
//        this.ourUsersList = ourUsersList;
//    }
//
//    public int getStatusCode() {
//        return statusCode;
//    }


}