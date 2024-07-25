package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.CenterAdminDto;
import net.primepro.primepro.dto.LoginDto;
import net.primepro.primepro.entity.CenterAdmin;
import net.primepro.primepro.mapper.CenterAdminMapper;
import net.primepro.primepro.repository.CenterAdminRepository;
import net.primepro.primepro.response.LoginResponse;
import net.primepro.primepro.service.CenterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CenterAdminServiceImpl implements CenterAdminService {

    private final CenterAdminRepository centerAdminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse loginCenterAdmin(LoginDto loginDto) {
        String msg = "";
        CenterAdmin centerAdmin1 = centerAdminRepository.findByEmail(loginDto.getEmail());
        if (centerAdmin1 != null) {
            String password = loginDto.getPassword();
            String encodedPassword = centerAdmin1.getPassword();

            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<CenterAdmin> centerAdmin = centerAdminRepository.findByEmailAndPassword(loginDto.getEmail(), encodedPassword);
                if (centerAdmin.isPresent()) {
                    return new LoginResponse("Login success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("Password does not match", false);
            }
        } else {
            return new LoginResponse("Email does not exist", false);
        }
    }

    @Override
    public CenterAdminDto addCenterAdmin(CenterAdminDto centerAdminDto) {
        CenterAdmin centerAdmin = CenterAdminMapper.mapToCenterAdmin(centerAdminDto, passwordEncoder);
        CenterAdmin savedOne = centerAdminRepository.save(centerAdmin);
        return CenterAdminMapper.mapToCenterAdminDto(savedOne);
    }

    @Override
    public void deleteCenterAdmin(Long centerAdminId) {
        centerAdminRepository.deleteById(centerAdminId);
    }

    @Override
    public CenterAdminDto editCenterAdmin(CenterAdminDto centerAdminDto) {
        // Implement the edit functionality
        return null;
    }

    @Override
    public List<CenterAdmin> viewAll() {
        return centerAdminRepository.findAll();
    }
}