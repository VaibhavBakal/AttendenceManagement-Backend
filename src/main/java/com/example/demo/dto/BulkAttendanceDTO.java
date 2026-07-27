package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class BulkAttendanceDTO {

    private LocalDate attendanceDate;

    private List<AttendanceItemDTO> attendanceList;
}