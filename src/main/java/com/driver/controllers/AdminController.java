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
	public ResponseEntity<Void> registerAdmin(@RequestBody Admin admin){
		adminService.adminRegister(admin);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Admin> updateAdminPassword(@RequestParam Integer adminId, @RequestParam String password){
		Admin updatedAdmin;
		try{
			updatedAdmin = adminService.updatePassword(adminId, password);
		}
		catch (Exception e){
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public void deleteAdmin(@RequestParam Integer adminId){
		try{
			adminService.deleteAdmin(adminId);
		}
		catch (Exception e){
			return;
		}
	}

	@GetMapping("/listOfCustomers")
	public List<Customer> listOfCustomers() {
		List<Customer> listOfCustomers;
		try{
			listOfCustomers = adminService.getListOfCustomers();
		}
		catch (Exception e){
			return null;
		}
		return listOfCustomers;
	}

	@GetMapping("/listOfDrivers")
	public List<Driver> listOfDrivers() {
		List<Driver> listOfDrivers;
		try{
			listOfDrivers = adminService.getListOfDrivers();
		}
		catch (Exception e){
			return null;
		}
		return listOfDrivers;
	}
}
