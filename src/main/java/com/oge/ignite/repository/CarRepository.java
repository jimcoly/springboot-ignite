package com.oge.ignite.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.springframework.data.domain.Pageable;

import com.oge.ignite.model.Car;

import java.util.List;

@RepositoryConfig(cacheName = "CarCache")
public interface CarRepository extends IgniteRepository<Car, Long> {
    List<Car> getCarsByName(String name);

    @Query("SELECT id FROM Car WHERE masterId = ?")
    List<Long> getCarsByMasterId(long id, Pageable pageable);
}
