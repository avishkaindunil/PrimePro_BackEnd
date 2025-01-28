package net.primepro.primepro.dto;


import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.primepro.primepro.constants.UserTypesEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String email;
    private String name;
    private String city;
    private UserTypesEnum role;
    private boolean isUserActivated;
}

