package net.primepro.primepro.controller;

import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.CenterAdminDto;
import net.primepro.primepro.dto.LoginDto;
import net.primepro.primepro.response.LoginResponse;
import net.primepro.primepro.service.CenterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/centerAdmin")
@AllArgsConstructor
public class CenterAdminController {

    @Autowired
    private CenterAdminService centerAdminService;

    @PostMapping("/add")
    public String createCenterAdmin(@RequestBody CenterAdminDto centerAdminDto) {
        centerAdminService.addCenterAdmin(centerAdminDto);
        return "CenterAdmin Added";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCenterAdmin(@RequestBody LoginDto loginDto) {
        LoginResponse loginResponse = centerAdminService.loginCenterAdmin(loginDto);
        return ResponseEntity.ok(loginResponse);
    }
}
