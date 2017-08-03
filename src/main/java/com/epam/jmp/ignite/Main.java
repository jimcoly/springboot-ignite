package com.epam.jmp.ignite;

import com.epam.jmp.ignite.config.SpringAppConfig;
import com.epam.jmp.ignite.model.Car;
import com.epam.jmp.ignite.model.Master;
import com.epam.jmp.ignite.repository.CarRepository;
import com.epam.jmp.ignite.repository.MasterRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Aliaksandr_Pleski
 * @since 8/2/2017.
 */
public class Main {
    private static AnnotationConfigApplicationContext ctx;
    private static CarRepository carRepository;
    private static MasterRepository masterRepository;

    public static void main(String[] args) {
        int mastersLength = 1000;
        int carsLength = 100000;
        if (args.length == 2) {
            mastersLength = Integer.valueOf(args[0]);
            carsLength = Integer.valueOf(args[1]);
        }
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(SpringAppConfig.class);
        ctx.refresh();

        masterRepository = ctx.getBean(MasterRepository.class);
        carRepository = ctx.getBean(CarRepository.class);

        createMasters(mastersLength);
        createCars(carsLength, mastersLength);
        System.out.println("----------------------------------------------\n");

        getMasterById(2L);

        getMastersByLastName("Petrov");

        getCarsByName("Opel");

        getCarsByMasterWithPagination(1L, 1, 2);

        getMastersByCarStatus(Car.Status.REGISTERED);

        ctx.destroy();
    }

    private static void getMastersByCarStatus(Car.Status status) {
        System.out.println("\nMaster with " + status + " cars:");
        masterRepository.getMastersByCarStatus(status).forEach(System.out::println);
    }

    private static void getMasterById(long id) {
        System.out.println("Master with id = " + id + ": " + masterRepository.getMasterById(id));
    }

    private static void getCarsByMasterWithPagination(long id, int page, int size) {
        System.out.println("\nCars of master with id " + id + ":");
        carRepository.getCarsByMasterId(id, new PageRequest(page, size)).forEach(System.out::println);
    }

    private static void getCarsByName(String carName) {
        System.out.println("\n" + carName + " cars:");
        carRepository.getCarsByName(carName).forEach(System.out::println);
    }

    private static void getMastersByLastName(String lastName) {
        System.out.println("\nMasters with last name " + lastName + ":");
        masterRepository.getMastersByLastName(lastName).forEach(System.out::println);
    }

    private static void createCars(int carsLength, int masterLength) {
        Map<Long, Car> cars = new TreeMap<>();

        String[] names = {"Opel", "Mercedes", "Ferrari", "Skoda", "Ford", "Jeep", "Toyota", "Audi", "Bmw", "VAZ"};
        for (int i = 0; i < carsLength; i++) {
            int id = i + 1;
            ThreadLocalRandom current = ThreadLocalRandom.current();
            String name = names[current.nextInt(names.length)];
            Car car = new Car(id, name, current.nextInt(50, 250), current.nextLong(1L, masterLength), Car.Status.getRandom());
            cars.put((long) id, car);
        }

        carRepository.save(cars);

        System.out.println(carsLength + " Cars created");
    }

    private static void createMasters(int mastersLength) {
        Map<Long, Master> masters = new TreeMap<>();

        String[] lastNames = {"Petrov", "Ivanov", "Sidorov", "Smith", "Zaitsev", "Korneev", "Bush", "Kush", "Kennedy", "Putin"};
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
