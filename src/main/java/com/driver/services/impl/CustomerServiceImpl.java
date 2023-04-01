package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer;
		try{
			customer = customerRepository2.findById(customerId).get();
		}
		catch (Exception e){
			throw new RuntimeException();
		}

		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		Customer customer;
		try{
			customer = customerRepository2.findById(customerId).get();
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}

		List<Driver> driverList = driverRepository2.findAll();
		for(Driver driver : driverList){
			if(driver.getCab().isAvailable()){
				TripBooking tripBooking = new TripBooking();
				tripBooking.setFromLocation(fromLocation);
				tripBooking.setToLocation(toLocation);
				tripBooking.setDistanceInKm(distanceInKm);
				tripBooking.setStatus(TripStatus.CONFIRMED);

				//get the cab
				Cab cab = driver.getCab();
				tripBooking.setBill(distanceInKm*cab.getPerKmRate());
				tripBooking.setCustomer(customer);

				//get the driver
				Driver driver1 = driverRepository2.findById(driver.getDriverId()).get();
				tripBooking.setDriver(driver1);

				cab.setAvailable(false);

				driver.getTripBookingList().add(tripBooking);
				customer.getTripBookingList().add(tripBooking);

				tripBookingRepository2.save(tripBooking);
				return tripBooking;
			}
		}

		throw new RuntimeException("No cab available!");
	}

	@Override
	public void cancelTrip(Integer tripId) {
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking;
		try{
			tripBooking = tripBookingRepository2.findById(tripId).get();
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}

		if(!tripBooking.getStatus().equals(TripStatus.CONFIRMED)){
			throw new RuntimeException();
		}
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBookingRepository2.save(tripBooking);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking;
		try{
			tripBooking = tripBookingRepository2.findById(tripId).get();
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}

		if(!tripBooking.getStatus().equals(TripStatus.CONFIRMED)){
			throw new RuntimeException();
		}
		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBookingRepository2.save(tripBooking);
	}
}
