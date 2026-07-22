package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.entity.Status;

import lombok.Data;

@Data
public class AttendanceRequestDTO {

    private Long studentId;
    private LocalDate attendanceDate;
    private Status status;

}