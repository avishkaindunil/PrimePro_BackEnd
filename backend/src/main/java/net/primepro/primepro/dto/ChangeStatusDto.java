package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangeStatusDto {
    private Long taskId;
    private String status;
}
