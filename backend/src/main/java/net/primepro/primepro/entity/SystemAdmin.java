package net.primepro.primepro.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "systemAdmin")
public class SystemAdmin {

    @Id
    private Integer id;

    private String email;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private OurUsers user;


}
