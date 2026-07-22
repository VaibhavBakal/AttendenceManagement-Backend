package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	
	List<Attendance> findByStudentId(Long studentId);
	List<Attendance> findByAttendanceDate(LocalDate date);

}
