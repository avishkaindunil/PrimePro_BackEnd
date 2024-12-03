package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignEmployeeDto {
    private Integer taskId;
    private Integer employeeId;
}
