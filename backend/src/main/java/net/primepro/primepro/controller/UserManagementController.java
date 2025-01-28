package net.primepro.primepro.controller;

import net.primepro.primepro.dto.LoginDto;
import net.primepro.primepro.dto.UserDto;
import net.primepro.primepro.repository.UsersRepo;
import org.springframework.web.bind.annotation.CrossOrigin;


import net.primepro.primepro.dto.ReqRes;
import net.primepro.primepro.entity.OurUsers;
import net.primepro.primepro.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins  = "http://localhost:5173/")
@RestController
public class UserManagementController {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg){
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

//    @GetMapping("/admin/get-all-users")
//    public ResponseEntity<ReqRes> getAllUsers(){
//        return ResponseEntity.ok(usersManagementService.getAllUsers());
//
//    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsersExcludingSystemAdmin());

    }


    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));
    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody OurUsers reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PutMapping("/admin/{userId}/toggle-activation")
    public ResponseEntity<String> toggleUserActivation(@PathVariable Integer userId) {
        usersManagementService.toggleUserActivation(userId);
        return ResponseEntity.ok("User activation status updated successfully");
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }

    @GetMapping("/admin/active-count")
    public ResponseEntity<Long> getActiveUserCount() {
        long activeUserCount = usersRepo.countByIsUserActivated(true);
        System.out.println("Active User Count: " + activeUserCount);
        return ResponseEntity.ok(activeUserCount);
    }
}


//@CrossOrigin(origins  = "http://localhost:5173/")