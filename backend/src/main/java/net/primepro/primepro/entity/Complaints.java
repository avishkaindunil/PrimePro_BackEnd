package net.primepro.primepro.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
@AllArgsConstructor
@Data
@Table(name="complaints")
public class Complaints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complaintId;
    private Integer UserID;
    private String complaint;
    private String mobile;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isResolved;

    public Complaints() {
    }

}