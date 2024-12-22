package net.primepro.primepro.service;

import net.primepro.primepro.dto.CenterAdminDto;
import net.primepro.primepro.dto.EmployeeDto;
import net.primepro.primepro.dto.LoginDto;
import net.primepro.primepro.dto.TaskDto;
import net.primepro.primepro.entity.Booking;
import net.primepro.primepro.entity.CenterAdmin;
import net.primepro.primepro.entity.Employee;
import net.primepro.primepro.response.BookingResponse;
import net.primepro.primepro.response.LoginResponse;

import java.util.List;

public interface CenterAdminService {


    LoginResponse loginCenterAdmin(LoginDto loginDto);

    CenterAdminDto addCenterAdmin(CenterAdminDto centerAdminDto);
    void deleteCenterAdmin(Integer centeradminId);
    CenterAdminDto editCenterAdmin(CenterAdminDto centerAdminDto);
    List<CenterAdmin> viewAll();

    List<BookingResponse> getAllBookings();

    List<Booking> getTodayBookings();
    List<BookingResponse> getTodayAllBookings();

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeDetails(String employeeId);

    List<?> getWorkLoadProgress();

    String assignTasks(TaskDto taskDto);

    String getCenter(int centerId);
}
