package net.primepro.primepro.service;

import jakarta.transaction.Transactional;
import net.primepro.primepro.constants.UserTypesEnum;
import net.primepro.primepro.dto.*;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.entity.OurUsers;
import net.primepro.primepro.entity.SystemAdmin;
import net.primepro.primepro.exception.EmailAlreadyExistsException;
import net.primepro.primepro.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersManagementService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CenterAdminService centerAdminService;  // Add CenterAdminService
    @Autowired
    private EmployeeService employeeService;  // Add EmployeeService
    @Autowired
    private SystemAdminService systemAdminService;

    @Transactional
    public ReqRes register(ReqRes registrationRequest){

        ReqRes resp = new ReqRes();

        if (usersRepo.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        try {
            OurUsers ourUser = new OurUsers();

            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setCity(registrationRequest.getCity());
            ourUser.setRole(UserTypesEnum.valueOf(registrationRequest.getRole()));
            ourUser.setName(registrationRequest.getName());
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            OurUsers ourUsersResult = usersRepo.save(ourUser);

            // Handle different roles
            if (ourUsersResult.getId() > 0) {
                if ("ADMIN".equals(registrationRequest.getRole())) {
                    CenterAdminDto centerAdminDto = new CenterAdminDto();
                    centerAdminDto.setId(Math.toIntExact(ourUsersResult.getId()));
                    centerAdminDto.setEmail(registrationRequest.getEmail());
                    centerAdminDto.setPassword(registrationRequest.getPassword());
                    centerAdminService.addCenterAdmin(centerAdminDto);
                } else if ("EMPLOYEE".equals(registrationRequest.getRole())) {
                    Employee employeeDto = new Employee();
                    employeeDto.setBranchName(registrationRequest.getBranchName());
                    employeeDto.setDateOfBirth(registrationRequest.getDateOfBirth());
                    employeeDto.setPhoneNumber(registrationRequest.getPhoneNumber());
                    employeeDto.setDesignation(registrationRequest.getDesignation());
                    employeeDto.setNic(registrationRequest.getNic());
                    employeeDto.setNoOfAnnualLeaves(registrationRequest.getNoOfAnnualLeaves());
                    employeeDto.setNoOfCasualLeaves(registrationRequest.getNoOfCasualLeaves());
                    employeeDto.setNoOfCasualLeaves(registrationRequest.getNoOfMedicalLeaves());
                    employeeDto.setBaseSalary(registrationRequest.getBaseSalary());
                    employeeDto.setProbation(registrationRequest.isProbation());
                    employeeService.addEmployee(employeeDto);
                } else if ("SYSTEMADMIN".equals(registrationRequest.getRole())) {
                    SystemAdmin systemAdmin = new SystemAdmin();
                    // Fetch the associated OurUsers entity
                    Optional<OurUsers> ourUserOptional = usersRepo.findById(ourUsersResult.getId());

                    if (ourUserOptional.isPresent()) {
                        OurUsers ourUser1 = ourUserOptional.get();
                        systemAdmin.setId(Math.toIntExact(ourUsersResult.getId())); // Set shared ID
                        systemAdmin.setEmail(registrationRequest.getEmail());
                        systemAdmin.setUser(ourUser1); // Associate the user

                        systemAdminService.addSystemAdmin(systemAdmin);
                    } else {
                        throw new RuntimeException("OurUsers entity not found for SystemAdmin");
                    }
                }

                resp.setOurUsers(ourUsersResult);
                resp.setMessage("Successfully Registered! Wait until you get the Approval");
                resp.setStatusCode(200);
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();

            if (!user.isUserActivated()) {
                throw new RuntimeException("User account is not activated. Please contact support.");
            }

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(String.valueOf(user.getRole()));
            response.setEmail(user.getEmail());
            response.setName(user.getName());
            response.setCity(user.getCity());
            response.setRefreshToken(refreshToken);
            response.setProfilePictureUrl(user.getProfilePictureUrl());
            response.setUserActivated(user.isUserActivated());
            response.setUserId(user.getId());

            if(user.getRole().equals(UserTypesEnum.EMPLOYEE)){
                response.setEmployeeId(user.getEmployee().getId());
                response.setEmployeeNumber(user.getEmployee().getEmployeeId());
                response.setBranchName(user.getEmployee().getBranchName());
                response.setDateOfBirth(user.getEmployee().getDateOfBirth());
                response.setPhoneNumber(user.getEmployee().getPhoneNumber());
                response.setDesignation(user.getEmployee().getDesignation());
                response.setNic(user.getEmployee().getNic());
                response.setNoOfAnnualLeaves(user.getEmployee().getNoOfAnnualLeaves());
                response.setNoOfCasualLeaves(user.getEmployee().getNoOfCasualLeaves());
                response.setNoOfMedicalLeaves(user.getEmployee().getNoOfMedicalLeaves());
                response.setBaseSalary(user.getEmployee().getBaseSalary());
                response.setProbation(user.getEmployee().isProbation());
            }

            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }





    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<OurUsers> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            OurUsers usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setOurUsers(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, OurUsers updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUsers savedUser = usersRepo.save(existingUser);
                reqRes.setOurUsers(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setOurUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }

    public List<UserDto> getAllUsersExcludingSystemAdmin() {
        return usersRepo.findAll()
                .stream()
                .filter(user -> user.getRole() != UserTypesEnum.SYSTEMADMIN)
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setId(user.getId());
                    dto.setEmail(user.getEmail());
                    dto.setName(user.getName());
                    dto.setCity(user.getCity());
                    dto.setRole(user.getRole());
                    dto.setUserActivated(user.isUserActivated());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public void toggleUserActivation(Integer userId) {
        OurUsers user = usersRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserActivated(!user.isUserActivated());
        usersRepo.save(user);
    }
}
