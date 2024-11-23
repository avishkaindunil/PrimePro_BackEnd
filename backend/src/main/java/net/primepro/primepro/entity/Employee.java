package net.primepro.primepro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private OurUsers user;

    @Column(name = "username")
    private String username;

    @Column(name = "centerID")
    private String centerID;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name="No_of_leaves")
    private Integer No_of_leaves;

    @Column(name="No_of_workdays")
    private Integer No_of_workdays;

    @Column(name = "email")
    private String email;

//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "id")
//    private OurUsers user;


//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "id")
//    private OurUsers user;
//
//    @ManyToOne
//    @JoinColumn(name = "center_id")
//    private CenterAdmin centerAdmin;

    public Employee() {
    }


    public Employee(Integer id, String username, String email, String centerID, String address, Integer noOfWorkdays, Integer noOfLeaves, String password, String phoneNo) {
    }

// Getters and setters...


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

//    public OurUsers getUser() {
//        return user;
//    }
//
//    public void setUser(OurUsers user) {
//        this.user = user;
//    }
//
//    public CenterAdmin getCenterAdmin() {
//        return centerAdmin;
//    }
//
//    public void setCenterAdmin(CenterAdmin centerAdmin) {
//        this.centerAdmin = centerAdmin;
//    }

}

