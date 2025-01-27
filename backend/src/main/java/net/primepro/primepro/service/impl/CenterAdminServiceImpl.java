package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.constants.BookingStatusEnum;
import net.primepro.primepro.constants.UserTypesEnum;
import net.primepro.primepro.dto.*;
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
    @Autowired private BookingRepo bookingRepo;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private TaskRepository taskRepository;
    @Autowired private LeaveRequestRepository leaveRequestRepository;


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

//    @Override
//    public List<Booking> getTodayBookings() {
//        List<Booking> todayBookings = new ArrayList<>();
//        try{
//            // Get current date (ignoring time)
//            LocalDate localDate = LocalDate.now();
//            Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//            LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            List<Booking> bookingList = bookingRepo.findAll();
//
//            for (Booking booking : bookingList) {
//                Date bookingDate = booking.getDate();
//                LocalDate bookingLocalDate = bookingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//                // Print out both dates to see the difference
//                System.out.println("Booking Date: " + bookingLocalDate);
//                System.out.println("Current Date: " + currentLocalDate);
//
//                // Compare only the date parts (ignoring time)
//                if (bookingLocalDate.equals(currentLocalDate)) {
////                    BookingResponse bookingResponse = new BookingResponse();
////                    bookingResponse.setBookingId((Integer) result[0]);
////                    bookingResponse.setCenterName((String) result[1]);
////                    bookingResponse.setUserId((Integer) result[2]);
////                    bookingResponse.setDate((Date) result[3]);
////                    bookingResponse.setCarName((String) result[4]);
////                    bookingResponse.setService((String) result[5]);
////                    bookingResponse.setCustomerId((Integer) result[6]);
////                    bookingResponse.setTaskDescription((String) result[7]);
////                    bookingResponse.setTaskDate((Date) result[8]);
////                    bookingResponse.setStartTime((Time) result[9]);
////                    bookingResponse.setEndTime((Time) result[10]);
////                    bookingResponse.setTaskStatus((String) result[11]);
////                    bookingResponse.setEmployeeId((String) result[12]);
//                    todayBookings.add(booking);
//                }
//            }
//        } catch (Exception e){
//            System.out.println("getTodayBookings | error : " +e.getMessage());
//        }
//        return todayBookings;
//    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employees = new ArrayList<>();
        try{
            List<?> employeeList = employeeRepository.findEmployeeDetails();
            for(Object employee : employeeList){
                Object[] result = (Object[]) employee;
                EmployeeDto employeeDto = new EmployeeDto();

                String employeeName = (String) result[0];
                int userId = (int) result[1];

                employeeDto.setName(employeeName);
                employeeDto.setId(userId);
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
            Integer empId = Integer.parseInt(employeeId);
            Object[] employee = (Object[]) employeeRepository.findByEmployeeId(empId);
            Integer allocatedSlot = centerAdminRepository.findAllocatedSlotCount(empId);
            if(employee != null && employee.length > 0) {
                employeeDto.setName((String) employee[0]);
                employeeDto.setEmail((String) employee[1]);
                employeeDto.setCity((String) employee[2]);
                employeeDto.setPhoneNumber((String) employee[3]);
                employeeDto.setDesignation((String) employee[4]);
                employeeDto.setPhoneNumber((String) employee[5]);
                employeeDto.setNic((String) employee[6]);
                employeeDto.setDateOfBirth((Date) employee[7]);
                employeeDto.setAnnualLeave((int) employee[8]);
                employeeDto.setCasualLeave((int) employee[9]);
            }
            if(allocatedSlot > 0){
                employeeDto.setCurrentAllocSlot(allocatedSlot);
            }
        } catch (Exception e){
            System.out.println("getEmployeeDetails | error :" +e.getMessage());
        }
        return employeeDto;
    }

    @Override
    public List<?> getWorkLoadProgress() {
        List<?> bookingsList = new ArrayList<>();
        try{
            bookingsList = centerAdminRepository.getWorkLoadProgress();
        } catch (Exception e){
            System.out.println("getWorkLoadProgress | error : " + e.getMessage());
        }
        return bookingsList;
    }

    @Transactional
    @Override
    public String assignTasks(TaskDto taskDto) {
        String responseMsg = null;
        try {
            Task task = new Task();
            task.setTaskStatus(String.valueOf(BookingStatusEnum.ACCEPTED));
            Optional<Booking> booking = bookingRepo.findById(taskDto.getBookingId());
            Optional<Employee> employee =  employeeRepository.findById(taskDto.getEmployeeId());
            task.setBooking(booking.get());
            task.setTaskDescription(taskDto.getTaskDescription());
            task.setCustomerId(taskDto.getCustomerId());
            task.setEmployee(employee.get());
            task.setTaskDate(taskDto.getTaskDate());
            task.setStartTime(taskDto.getStartTime());
            task.setEndTime(taskDto.getEndTime());
            taskRepository.save(task);

            bookingRepo.updateTaskStatus(true, taskDto.getBookingId());
            responseMsg = "SUCCESS";
        } catch (Exception e){
            System.out.println("assignTasks | error : " + e.getMessage());
        }
        return responseMsg;
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

    // --------------------------- New development ------------------------------- //
//    public List<Booking> getBookingsWithoutTimeAllocation() {
//        return centerAdminRepository.findBookingsWithoutTimeAllocation();
//    }

    public Booking allocateTime(Integer bookingId, Time startTime) {
        Optional<Booking> bookingOptional = bookingRepo.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            throw new IllegalArgumentException("Booking not found with id: " + bookingId);
        }
        Booking booking = bookingOptional.get();
//        booking.setTime(startTime);
//        booking.setBookingChecked(true);
//        booking.setTimeAllocatable(true);
        return bookingRepo.save(booking);
    }

    @Override
    public Booking cantAllocateTime(Integer bookingId) {
        Optional<Booking> bookingOptional = bookingRepo.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            throw new IllegalArgumentException("Booking not found with id: " + bookingId);
        }
        Booking booking = bookingOptional.get();
//        booking.setTime(null);
//        booking.setBookingChecked(true);
//        booking.setTimeAllocatable(false);
        return bookingRepo.save(booking);
    }

//    @Override
//    public List<Booking> getBookingsWithoutTaskAssigned() {
//        return centerAdminRepository.findBookingsWithoutTaskAssigned();
//    }

    @Override
    public List<BookingResponse> getTodayAllBookings() {
        List<BookingResponse> todayBookings = new ArrayList<>();
        try{
            // Get current date (ignoring time)
            LocalDate localDate = LocalDate.now();
            Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Convert currentDate to LocalDate for comparison
            LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            List<?> bookingList = centerAdminRepository.getWorkLoadProgress();

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
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public List<EmpPerformDto> getEmployeePerform() {
        List<EmpPerformDto> empPerformDtoList = new ArrayList<>();
        List<Object[]> performList = centerAdminRepository.getEmployeePerform();

        for (Object[] result : performList) {
            EmpPerformDto empPerformDto = new EmpPerformDto();
            empPerformDto.setEmployeeName((String) result[0]);
            empPerformDto.setCount((Long) result[1]);
            empPerformDtoList.add(empPerformDto);
        }
        return empPerformDtoList;
    }

    @Override
    public List<TaskDisDto> getTaskDistribution() {
        List<TaskDisDto> taskDistributions = new ArrayList<>();
        List<Object[]> tasks = centerAdminRepository.getTaskDistribution();

        for(Object[] result : tasks){
            TaskDisDto taskDisDto = new TaskDisDto();
            taskDisDto.setDayOfWeek((String) result[0]);
            taskDisDto.setCompletedTaskCount((Long) result[1]);
            taskDistributions.add(taskDisDto);
        }
        return taskDistributions;
    }
}
