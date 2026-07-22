package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AttendanceRequestDTO;
import com.example.demo.dto.BulkAttendanceDTO;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.Student;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    @Override
    public Attendance markAttendance(AttendanceRequestDTO request) {

        // Fetch student from database
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create attendance object
        Attendance attendance = Attendance.builder()
                .student(student)
                .attendanceDate(request.getAttendanceDate())
                .status(request.getStatus())
                .build();

        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    @Override
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date);
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public void markBulkAttendance(
            BulkAttendanceDTO dto) {

        for(Long studentId : dto.getStudentIds()) {

            Student student =
                studentRepository.findById(studentId)
                .orElseThrow(
                  () -> new RuntimeException(
                    "Student Not Found"));

            Attendance attendance =
                Attendance.builder()
                    .student(student)
                    .attendanceDate(
                       dto.getAttendanceDate())
                    .status(dto.getStatus())
                    .build();

            attendanceRepository.save(attendance);
        }
    }
}