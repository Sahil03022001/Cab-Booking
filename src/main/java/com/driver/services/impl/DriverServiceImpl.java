package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
		Driver driver = new Driver();
		driver.setMobile(mobile);
		driver.setPassword(password);

		//details for cab
		Cab cab = new Cab();
		cab.setPerKmRate(10);
		cab.setAvailable(true);
		cab.setDriver(driver);
		driver.setCab(cab);

		driverRepository3.save(driver);
	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
		Driver driver;
		try{
			driver = driverRepository3.findById(driverId).get();
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}

		driverRepository3.delete(driver);
	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective cab to unavailable
		Driver driver;
		try{
			driver = driverRepository3.findById(driverId).get();
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}

		Cab cab = driver.getCab();
		cab.setAvailable(false);
		driverRepository3.save(driver);
	}
}