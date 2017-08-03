package com.epam.jmp.ignite.repository;

import com.epam.jmp.ignite.model.Car;
import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Aliaksandr_Pleski
 * @since 8/2/2017.
 */
@RepositoryConfig(cacheName = "CarCache")
public interface CarRepository extends IgniteRepository<Car, Long> {
    List<Car> getCarsByName(String name);

    @Query("SELECT id FROM Car WHERE masterId = ?")
    List<Long> getCarsByMasterId(long id, Pageable pageable);
}
