package com.oge.ignite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oge.ignite.model.Master;
import com.oge.ignite.repository.MasterRepository;

@RestController
public class MasterController {
	
	@Autowired
	MasterRepository masterRepo;
	
	@GetMapping("/master/list")
	public List<Master> listFlight() {
		Iterable<Master> masterItr = masterRepo.findAll();
		List<Master> list = new ArrayList<Master>();
		masterItr.forEach(m -> {
			list.add(m);
		});
		return list;
	}
}
