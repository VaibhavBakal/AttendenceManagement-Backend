package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entity.Status;

import lombok.Data;

@Data
public class BulkAttendanceDTO {

	private List<Long> studentIds;
	private LocalDate attendanceDate;
	private Status status;
}