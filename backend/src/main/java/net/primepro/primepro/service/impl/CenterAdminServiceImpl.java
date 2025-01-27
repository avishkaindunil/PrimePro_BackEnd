package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.constants.BookingStatusEnum;
import net.primepro.primepro.constants.UserTypesEnum;
import net.primepro.primepro.dto.CenterAdminDto;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.dto.LoginDto;
import net.primepro.primepro.dto.TaskDto;
import net.primepro.primepro.entity.*;
import net.primepro.primepro.mapper.CenterAdminMapper;
import net.primepro.primepro.repository.*;
import net.primepro.primepro.response.BookingResponse;
import net.primepro.primepro.response.LoginResponse;
import net.primepro.primepro.service.CenterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CenterAdminServiceImpl implements CenterAdminService {

    private final CenterAdminRepository centerAdminRepository;
    private UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaskRepository taskRepository;

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
        //    CenterAdmin centerAdmin = CenterAdminMapper.mapToCenterAdmin(centerAdminDto, passwordEncoder);
        CenterAdmin centerAdmin = new CenterAdmin();
        centerAdmin.setUser(centerAdminDto.getOurUsers());
        centerAdmin.setCenterName(centerAdminDto.getUsername());
        centerAdmin.setPassword(passwordEncoder.encode(centerAdminDto.getPassword()));
        centerAdmin.setEmail(centerAdminDto.getEmail());

        CenterAdmin savedOne = centerAdminRepository.save(centerAdmin);
        return CenterAdminMapper.mapToCenterAdminDto(savedOne);
    }

    @Override
    public void deleteCenterAdmin(Integer centerAdminId) {

        centerAdminRepository.deleteById(centerAdminId);
        usersRepo.deleteById(centerAdminId);
    }

    @Override
    public CenterAdminDto editCenterAdmin(CenterAdminDto centerAdminDto) {
        CenterAdmin centerAdmin = CenterAdminMapper.mapToCenterAdmin(centerAdminDto, passwordEncoder);
        CenterAdmin savedAdmin = centerAdminRepository.save(centerAdmin);

        // Update the OurUsers record
        Optional<OurUsers> ourUserOptional = usersRepo.findById(savedAdmin.getId());
        if (ourUserOptional.isPresent()) {
            OurUsers ourUser = ourUserOptional.get();
            ourUser.setEmail(savedAdmin.getEmail());
            ourUser.setRole(UserTypesEnum.valueOf("ADMIN"));  // Assuming role is ADMIN
            usersRepo.save(ourUser);
        }

        return CenterAdminMapper.mapToCenterAdminDto(savedAdmin);

    }

    @Override
    public List<CenterAdmin> viewAll() {
        return centerAdminRepository.findAll();
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        List<BookingResponse> bookingsList = new ArrayList<>();
        try{
          List<?> bookings = bookingRepo.getBookingsDetails();
          for(Object booking : bookings){
              Object[] result = (Object[]) booking;
              BookingResponse bookingResponse = new BookingResponse();

              bookingResponse.setBookingId((Integer) result[0]);
              bookingResponse.setCenterName((String) result[1]);
              bookingResponse.setUserId((Integer) result[2]);
              bookingResponse.setDate((Date) result[3]);
              bookingResponse.setCarName((String) result[4]);
              bookingResponse.setService((String) result[5]);
              bookingResponse.setCustomerId((Integer) result[6]);
              bookingResponse.setTaskDescription((String) result[7]);
              bookingResponse.setTaskDate((Date) result[8]);
              bookingResponse.setStartTime((Time) result[9]);
              bookingResponse.setEndTime((Time) result[10]);
              bookingResponse.setTaskStatus((String) result[11]);
              bookingResponse.setEmployeeId((String) result[12]);
              bookingsList.add(bookingResponse);
          }
        } catch (Exception e){
            System.out.println("getAllBookings | error : " +e.getMessage());
        }
        return bookingsList;
    }

    @Override
    public List<BookingResponse> getTodayAllBookings() {
        List<BookingResponse> todayBookings = new ArrayList<>();
        try{
            // Get current date (ignoring time)
            LocalDate localDate = LocalDate.now();
            Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Convert currentDate to LocalDate for comparison
            LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            List<?> bookingList = bookingRepo.getWorkLoadProgress();

            for (Object booking : bookingList) {
                Object[] result = (Object[]) booking;
                Date bookingDate = (Date) result[3];
                // Convert booking date to LocalDate
                LocalDate bookingLocalDate =  bookingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Print out both dates to see the difference
                System.out.println("Booking Date: " + bookingLocalDate);
                System.out.println("Current Date: " + currentLocalDate);

                // Compare only the date parts (ignoring time)
                if (bookingLocalDate.equals(currentLocalDate)) {
                    BookingResponse bookingResponse = new BookingResponse();
                    bookingResponse.setBookingId((Integer) result[0]);
                    bookingResponse.setCenterName((String) result[1]);
                    bookingResponse.setUserId((Integer) result[2]);
                    bookingResponse.setDate((Date) result[3]);
                    bookingResponse.setCarName((String) result[4]);
                    bookingResponse.setService((String) result[5]);
                    bookingResponse.setCustomerId((Integer) result[6]);
                    bookingResponse.setTaskDescription((String) result[7]);
                    bookingResponse.setTaskDate((Date) result[8]);
                    bookingResponse.setStartTime((Time) result[9]);
                    bookingResponse.setEndTime((Time) result[10]);
                    bookingResponse.setTaskStatus((String) result[11]);
                    bookingResponse.setEmployeeId((String) result[12]);
                    todayBookings.add(bookingResponse);
                }
            }
        } catch (Exception e){
            System.out.println("getTodayBookings | error : " +e.getMessage());
        }
        return todayBookings;
    }

    @Override
    public List<Booking> getTodayBookings() {
        List<Booking> todayBookings = new ArrayList<>();
        try {
            // Get current date (ignoring time)
            LocalDate currentLocalDate = LocalDate.now();  // No need for conversion here

            // Fetch all bookings
            List<Booking> bookingList = bookingRepo.findAll();

            // Iterate through each booking and filter by today's date
            for (Booking booking : bookingList) {
                // Get the booking date (assuming it's already a LocalDate)
                LocalDate bookingDate = booking.getDate();

                // Print out both dates to see the difference (for debugging)
                System.out.println("Booking Date: " + bookingDate);
                System.out.println("Current Date: " + currentLocalDate);

                // Compare only the date parts (ignoring time)
                if (bookingDate.equals(currentLocalDate)) {
                    todayBookings.add(booking);
                }
            }
        } catch (Exception e) {
            System.out.println("getTodayBookings | error : " + e.getMessage());
        }
        return todayBookings;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employees = new ArrayList<>();
        try{
            List<?> employeeList = employeeRepository.findEmployeeDetails();
            for(Object employee : employeeList){
                Object[] result = (Object[]) employee;
                EmployeeDto employeeDto = new EmployeeDto();

                String employeeName = (String) result[0];
                int employeeId = (int) result[1];

                employeeDto.setName(employeeName);
                employeeDto.setId(employeeId);
                employees.add(employeeDto);
            }
        } catch (Exception e){
            System.out.println("getAllEmployees | error :" +e.getMessage());
        }
        return employees;
    }

    @Override
    public EmployeeDto getEmployeeDetails(String employeeId) {
        EmployeeDto employeeDto = new EmployeeDto();
        try{
            Employee employee = employeeRepository.findByEmployeeId(employeeId);
            employeeDto.setEmployeeId(employee.getEmployeeId());
            employeeDto.setEmail(employee.getUser().getEmail());
            employeeDto.setName(employee.getUser().getName());
            employeeDto.setCity(employee.getUser().getCity());
            employeeDto.setRole(employee.getUser().getRole());
            employeeDto.setProfilePictureUrl(employee.getUser().getProfilePictureUrl());
            employeeDto.setUserActivated(employee.getUser().isUserActivated());
            employeeDto.setBranchName(employee.getBranchName());
            employeeDto.setDateOfBirth(employee.getDateOfBirth());
            employeeDto.setPhoneNumber(employee.getPhoneNumber());
            employeeDto.setDesignation(employee.getDesignation());
        } catch (Exception e){
            System.out.println("getEmployeeDetails | error :" +e.getMessage());
        }
        return employeeDto;
    }

    @Override
    public List<?> getWorkLoadProgress() {
        List<?> bookingsList = new ArrayList<>();
        try{
            bookingsList = bookingRepo.getWorkLoadProgress();
        } catch (Exception e){
            System.out.println("getWorkLoadProgress | error : " + e.getMessage());
        }
        return bookingsList;
    }

    @Transactional
    @Override
    public String assignTasks(TaskDto taskDto) {
        String responseMsz = null;
        try {
            Task task = new Task();
            task.setTaskStatus(String.valueOf(BookingStatusEnum.ACCEPTED));
            Optional<Booking> booking = bookingRepo.findById(taskDto.getBookingId());
            Optional<Employee> employee =  employeeRepository.findById(taskDto.getEmployeeId());
            task.setBooking(booking.get());
            task.setTaskDescription(taskDto.getTaskDescription());
            task.setTaskDate(taskDto.getTaskDate());
            task.setStartTime(taskDto.getStartTime());
            task.setEndTime(taskDto.getEndTime());
            task.setCustomerId(taskDto.getCustomerId());
            task.setEmployee(employee.get());
            taskRepository.save(task);

            bookingRepo.updateTaskStatus(true, taskDto.getBookingId());
            responseMsz = "SUCCESS";
        } catch (Exception e){
            System.out.println("assignTasks | error : " + e.getMessage());
        }
        return responseMsz;
    }

    @Override
    public String getCenter(int centerId) {
       return centerAdminRepository.findCenterName(centerId);
    }

    @Override
    public List<Booking> getBookings() {
        try {
            List<Booking> allBookings = bookingRepo.findAll();

            LocalDate today = LocalDate.now();

            // Filter the bookings by checking the date and task assignment status
            return allBookings.stream()
                    .filter(booking -> {
                        // Directly compare the LocalDate
                        return !booking.getDate().isBefore(today) && !booking.isTaskAssigned();
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Error in getBookings: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}

