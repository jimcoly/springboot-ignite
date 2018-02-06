package com.oge.ignite;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oge.ignite.model.Car;
import com.oge.ignite.model.Master;
import com.oge.ignite.repository.CarRepository;
import com.oge.ignite.repository.MasterRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {

	@Autowired
	CarRepository carRepository;
	@Autowired
	MasterRepository masterRepository;

	@Test
	public void initData() {
		int mastersLength = 1000;
		int carsLength = 100000;

		createMasters(mastersLength);
		createCars(carsLength, mastersLength);
		System.out.println("----------------------------------------------\n");

		getMasterById(2L);

		getMastersByLastName("Petrov");

		getCarsByName("Opel");

		getCarsByMasterWithPagination(1L, 1, 2);

		getMastersByCarStatus(Car.Status.REGISTERED);

	}

	private void getMastersByCarStatus(Car.Status status) {
		System.out.println("\nMaster with " + status + " cars:");
		masterRepository.getMastersByCarStatus(status).forEach(System.out::println);
	}

	private void getMasterById(long id) {
		System.out.println("Master with id = " + id + ": " + masterRepository.getMasterById(id));
	}

	private void getCarsByMasterWithPagination(long id, int page, int size) {
		System.out.println("\nCars of master with id " + id + ":");
		carRepository.getCarsByMasterId(id, new PageRequest(page, size)).forEach(System.out::println);
	}

	private void getCarsByName(String carName) {
		System.out.println("\n" + carName + " cars:");
		carRepository.getCarsByName(carName).forEach(System.out::println);
	}

	private void getMastersByLastName(String lastName) {
		System.out.println("\nMasters with last name " + lastName + ":");
		masterRepository.getMastersByLastName(lastName).forEach(System.out::println);
	}

	private void createCars(int carsLength, int masterLength) {
		Map<Long, Car> cars = new TreeMap<>();

		String[] names = { "Opel", "Mercedes", "Ferrari", "Skoda", "Ford", "Jeep", "Toyota", "Audi", "Bmw", "VAZ" };
		for (int i = 0; i < carsLength; i++) {
			int id = i + 1;
			ThreadLocalRandom current = ThreadLocalRandom.current();
			String name = names[current.nextInt(names.length)];
			Car car = new Car(id, name, current.nextInt(50, 250), current.nextLong(1L, masterLength),
					Car.Status.getRandom());
			cars.put((long) id, car);
		}

		carRepository.save(cars);

		System.out.println(carsLength + " Cars created");
	}

	private void createMasters(int mastersLength) {
		Map<Long, Master> masters = new TreeMap<>();

		String[] lastNames = { "Petrov", "Ivanov", "Sidorov", "Smith", "Zaitsev", "Korneev", "Bush", "Kush", "Kennedy",
				"Putin" };
		for (int i = 0; i < mastersLength; i++) {
			int id = i + 1;
			ThreadLocalRandom current = ThreadLocalRandom.current();
			String lastName = lastNames[current.nextInt(lastNames.length)];
			Master master = new Master(id, "Alex" + id, lastName, current.nextInt(1000, 2000));
			masters.put((long) id, master);
		}

		masterRepository.save(masters);

		System.out.println(mastersLength + " Masters created");
	}
}
