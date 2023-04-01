package com.driver.controllers;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.model.Admin;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@PostMapping("/register")
	public ResponseEntity<String> registerAdmin(@RequestBody Admin admin){
		adminService.adminRegister(admin);
		return new ResponseEntity<>("Admin Registered",HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateAdminPassword(@RequestParam Integer adminId, @RequestParam String password){
		Admin updatedAdmin;
		try{
			updatedAdmin = adminService.updatePassword(adminId, password);
		}
		catch (Exception e){
			return new ResponseEntity<>("Admin not found", HttpStatus.OK);
		}
		return new ResponseEntity<>("Password Updated", HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteAdmin(@RequestParam Integer adminId){
		try{
			adminService.deleteAdmin(adminId);
		}
		catch (Exception e){
			return new ResponseEntity<>("Admin not found", HttpStatus.OK);
		}
		return new ResponseEntity<>("Admin deleted", HttpStatus.OK);
	}

	@GetMapping("/listOfCustomers")
	public List<Customer> listOfCustomers() {
		return adminService.getListOfCustomers();
	}

	@GetMapping("/listOfDrivers")
	public List<Driver> listOfDrivers() {
		return adminService.getListOfDrivers();
	}
}
