//package net.primepro.primepro.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.Date;
//import java.util.Set;
//
//@Getter
//@Setter
//@Entity
//@AllArgsConstructor
//
//@Table(name = "centerAdmin")
//public class CenterAdmin {
//
//    @Id
//    private Long id;
//
//    @Column(name = "username")
//    private String username;
//
//    @Column(name = "centerName")
//    private String centerName;
//
//    @Column(name = "address")
//    private String address;
//
//    @Column(name = "BRNo")
//    private String BRNo;
//
//    @Column(name = "phoneNo")
//    private String phoneNo;
//
//    @Column(name = "registeredDate")
//    private Date registeredDate;
//
//    @Column(name = "email")
//    private String email;
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
//    private OurUsers user;
//
//    @OneToMany(mappedBy = "centerAdmin")
//    private Set<Employee> employees;
//
//    public CenterAdmin() {
//    }
//
//    public CenterAdmin(Long id, String username, String password, String email) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//
//    }
//
//    // Getters and setters...
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
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
//    public String getCenterName() {
//        return centerName;
//    }
//
//    public void setCenterName(String centerName) {
//        this.centerName = centerName;
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
//    public String getBRNo() {
//        return BRNo;
//    }
//
//    public void setBRNo(String BRNo) {
//        this.BRNo = BRNo;
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
//    public Date getRegisteredDate() {
//        return registeredDate;
//    }
//
//    public void setRegisteredDate(Date registeredDate) {
//        this.registeredDate = registeredDate;
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
//    public OurUsers getUser() {
//        return user;
//    }
//
//    public void setUser(OurUsers user) {
//        this.user = user;
//    }
//
//    public Set<Employee> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(Set<Employee> employees) {
//        this.employees = employees;
//    }
//}


package net.primepro.primepro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "centerAdmin")
public class CenterAdmin {

    @Id
     private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name = "centerName")
    private String centerName;

    @Column(name = "address")
    private String address;

    @Column(name = "BRNo")
    private String BRNo;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name = "registeredDate")
    private Date registeredDate;

    @Column(name = "email")
    private String email;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private OurUsers user;

//    @Setter
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
//    private OurUsers user;
//
//    @OneToMany(mappedBy = "centerAdmin")
//    private Set<Employee> employees;

    public CenterAdmin() {
    }

    public CenterAdmin(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public CenterAdmin(Integer id, String username, String encode, String email) {
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

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBRNo() {
        return BRNo;
    }

    public void setBRNo(String BRNo) {
        this.BRNo = BRNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
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
//    public Set<Employee> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(Set<Employee> employees) {
//        this.employees = employees;
//    }


}

