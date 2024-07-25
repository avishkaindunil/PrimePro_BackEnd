package net.primepro.primepro.service;

import net.primepro.primepro.dto.CenterAdminDto;
import net.primepro.primepro.dto.LoginDto;
import net.primepro.primepro.entity.CenterAdmin;
import net.primepro.primepro.response.LoginResponse;

import java.util.List;

public interface CenterAdminService {


     LoginResponse loginCenterAdmin(LoginDto loginDto);

    CenterAdminDto addCenterAdmin(CenterAdminDto centerAdminDto);
    void deleteCenterAdmin(Long centeradminId);
    CenterAdminDto editCenterAdmin(CenterAdminDto centerAdminDto);
    List<CenterAdmin> viewAll();
}
