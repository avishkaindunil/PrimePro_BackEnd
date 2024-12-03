package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCountByMonth {
    private int year;
    private String monthName;
    private Long taskCount;
}
