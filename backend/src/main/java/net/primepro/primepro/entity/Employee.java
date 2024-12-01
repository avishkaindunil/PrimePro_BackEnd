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

    @PrePersist
    public void generateEmployeeId() {
        this.employeeId = "EMP-" + UUID.randomUUID().toString();
    }
}


