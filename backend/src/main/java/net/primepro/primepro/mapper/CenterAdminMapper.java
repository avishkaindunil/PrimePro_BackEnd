package net.primepro.primepro.mapper;

import net.primepro.primepro.dto.CenterAdminDto;
import net.primepro.primepro.entity.CenterAdmin;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CenterAdminMapper {

    public static CenterAdminDto mapToCenterAdminDto(CenterAdmin centerAdmin) {
        return new CenterAdminDto(
                centerAdmin.getId(),
                centerAdmin.getUsername(),
                centerAdmin.getPassword(),
                centerAdmin.getEmail()
        );
    }

    public static CenterAdmin mapToCenterAdmin(CenterAdminDto centerAdminDto, PasswordEncoder passwordEncoder) {
        return new CenterAdmin(
                centerAdminDto.getId(),
                centerAdminDto.getUsername(),
                passwordEncoder.encode(centerAdminDto.getPassword()),
                centerAdminDto.getEmail()
        );
    }
}
