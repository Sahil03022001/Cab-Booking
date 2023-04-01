package com.driver.controllers;

import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/driver")
public class DriverController {

	@Autowired
	DriverService driverService;

	@PostMapping(value = "/register")
	public ResponseEntity<String> registerDriver(@RequestParam String mobile, @RequestParam String password){
		driverService.register(mobile, password);
		return new ResponseEntity<>("Driver registered", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteDriver(@RequestParam Integer driverId){
		try{
			driverService.removeDriver(driverId);
		}
		catch (Exception e){
			return new ResponseEntity<>("Driver not found", HttpStatus.OK);
		}
		return new ResponseEntity<>("Driver deleted", HttpStatus.OK);
	}

	@PutMapping("/status")
	public ResponseEntity<String> updateStatus(@RequestParam Integer driverId){
		try{
			driverService.updateStatus(driverId);
		}
		catch (Exception e){
			return new ResponseEntity<>("Driver not found", HttpStatus.OK);
		}
		return new ResponseEntity<>("Driver's cab status updated", HttpStatus.OK);
	}
}
