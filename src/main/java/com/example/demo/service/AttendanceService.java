package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.AttendanceRequestDTO;
import com.example.demo.dto.BulkAttendanceDTO;
import com.example.demo.entity.Attendance;

public interface AttendanceService {

    Attendance markAttendance(AttendanceRequestDTO request);

    List<Attendance> getAttendanceByStudent(Long studentId);

    List<Attendance> getAttendanceByDate(LocalDate date);

    List<Attendance> getAllAttendance();
    
    void markBulkAttendance(BulkAttendanceDTO dto);
}
