package net.primepro.primepro.dto;


import lombok.*;
import net.primepro.primepro.entity.OurUsers;

@Data

@AllArgsConstructor
@NoArgsConstructor

public class CenterAdminDto {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private OurUsers ourUsers;


    public CenterAdminDto(Integer id, String username, String email, String centerName) {
    }
}
//    public CenterAdminDto(Integer id, String username, String email, String centerName) {
//    }

//    public CenterAdminDto(Long id, String username, String email) {
//    }

//    public CenterAdminDto(Integer id, String username, String email, String centerName) {
//    }


//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public CenterAdminDto() {
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
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
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }



