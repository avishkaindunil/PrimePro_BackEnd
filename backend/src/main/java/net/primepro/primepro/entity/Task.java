package net.primepro.primepro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonIgnore
    private Employee employee;

    private String taskDescription;
    private LocalDate taskDate;
    private Time startTime;
    private Time endTime;
    private String taskStatus;
}
