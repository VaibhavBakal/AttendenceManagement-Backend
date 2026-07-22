package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AttendanceRequestDTO;
import com.example.demo.dto.BulkAttendanceDTO;
import com.example.demo.entity.Attendance;
import com.example.demo.service.AttendanceService;

import lombok.RequiredArgsConstructor;


import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

	private final AttendanceService attendanceService;

	@PostMapping
	public Attendance markAttendance(@RequestBody AttendanceRequestDTO request) {
		return attendanceService.markAttendance(request);
	}

	@GetMapping("/student/{studentId}")
	public List<Attendance> getByStudent(@PathVariable Long studentId) {
		return attendanceService.getAttendanceByStudent(studentId);
	}

	@GetMapping("/date/{date}")
	public List<Attendance> getByDate(@PathVariable LocalDate date) {
		return attendanceService.getAttendanceByDate(date);
	}

	@GetMapping
	public List<Attendance> getAll() {
		return attendanceService.getAllAttendance();
	}

	@PostMapping("/bulk")
	public String markBulkAttendance(@RequestBody BulkAttendanceDTO dto) {

		attendanceService.markBulkAttendance(dto);

		return "Attendance Saved";
	}
	
	@GetMapping("/export/{date}")
	public ResponseEntity<byte[]> exportAttendance(
	        @PathVariable LocalDate date) throws Exception {

	    List<Attendance> attendanceList =
	            attendanceService.getAttendanceByDate(date);

	    Workbook workbook = new XSSFWorkbook();

	    Sheet sheet = workbook.createSheet("Attendance");

	    Row header = sheet.createRow(0);

	    header.createCell(0).setCellValue("ID");
	    header.createCell(1).setCellValue("Student Name");
	    header.createCell(2).setCellValue("Email");
	    header.createCell(3).setCellValue("Date");
	    header.createCell(4).setCellValue("Status");

	    int rowNum = 1;

	    for (Attendance attendance : attendanceList) {

	        Row row = sheet.createRow(rowNum++);

	        row.createCell(0).setCellValue(attendance.getId());
	        row.createCell(1).setCellValue(attendance.getStudent().getName());
	        row.createCell(2).setCellValue(attendance.getStudent().getEmail());
	        row.createCell(3).setCellValue(attendance.getAttendanceDate().toString());
	        row.createCell(4).setCellValue(attendance.getStatus().toString());
	    }

	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    workbook.write(out);
	    workbook.close();

	    HttpHeaders headers = new HttpHeaders();

	    headers.add(
	            HttpHeaders.CONTENT_DISPOSITION,
	            "attachment; filename=attendance.xlsx"
	    );

	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentType(
	                    MediaType.APPLICATION_OCTET_STREAM)
	            .body(out.toByteArray());
	}
	
	

}