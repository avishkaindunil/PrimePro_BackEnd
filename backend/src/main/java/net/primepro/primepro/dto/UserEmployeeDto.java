package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEmployeeDto {
    private String name;
    private Date dateOfBirth;
    private String phoneNumber;
    private String profilePictureUrl;
}
