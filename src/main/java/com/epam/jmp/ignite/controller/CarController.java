package com.epam.jmp.ignite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.jmp.ignite.model.Car;
import com.epam.jmp.ignite.repository.CarRepository;

@RestController
public class CarController {
	
	@Autowired
	CarRepository carRepo;
	
	@GetMapping("/car/list")
	public List<Car> listFlight() {
		Iterable<Car> carItr = carRepo.findAll();
		List<Car> list = new ArrayList<Car>();
		carItr.forEach(c -> {
			list.add(c);
		});
		return list;
	}
}
