//package net.primepro.primepro.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//package net.primepro.primepro.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Entity
//@Table(name = "ourusers")
//@Data
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class OurUsers implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String email;
//    private String password;
//    private String role;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private CenterAdmin centerAdmin;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Employee employee;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role));
//    }
//
//
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//
//}



package net.primepro.primepro.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ourusers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OurUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CenterAdmin centerAdmin;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
