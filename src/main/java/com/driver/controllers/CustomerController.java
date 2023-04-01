package com.driver.controllers;

import com.driver.model.Customer;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){
		customerService.register(customer);
		return new ResponseEntity<>("Customer registered", HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCustomer(@RequestParam Integer customerId){
		try{
			customerService.deleteCustomer(customerId);
		}
		catch (Exception e){
			return new ResponseEntity<>("Customer not found", HttpStatus.OK);
		}
		return new ResponseEntity<>("Customer deleted", HttpStatus.OK);
	}

	@PostMapping("/bookTrip")
	public ResponseEntity bookTrip(@RequestParam Integer customerId, @RequestParam String fromLocation, @RequestParam String toLocation, @RequestParam Integer distanceInKm) throws Exception {
		TripBooking bookedTrip;
		try{
			bookedTrip = customerService.bookTrip(customerId, fromLocation, toLocation, distanceInKm);
		}
		catch (Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		return new ResponseEntity<>(bookedTrip.getTripBookingId(), HttpStatus.CREATED);
	}

	@DeleteMapping("/complete")
	public ResponseEntity<String> completeTrip(@RequestParam Integer tripId){
		try{
			customerService.completeTrip(tripId);
		}
		catch (Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Trip is Completed", HttpStatus.OK);
	}

	@DeleteMapping("/cancelTrip")
	public ResponseEntity<String> cancelTrip(@RequestParam Integer tripId){
		try{
			customerService.cancelTrip(tripId);
		}
		catch (Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Trip is Canceled", HttpStatus.OK);
	}
}
