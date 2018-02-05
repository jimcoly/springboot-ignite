package com.oge.ignite.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;

import com.oge.ignite.model.Car;
import com.oge.ignite.model.Master;

import java.util.List;

/**
 * @author Aliaksandr_Pleski
 * @since 8/2/2017.
 */
@RepositoryConfig(cacheName = "MasterCache")
public interface MasterRepository extends IgniteRepository<Master, Long> {
    Master getMasterById(Long id);

    List<Master> getMastersByLastName(String lastName);

    @Query("SELECT m.* from Master as m, \"CarCache\".Car as car\n" +
            "  where m.id = car.masterId \n" +
            "  and car.status = ? \n" +
            "GROUP BY m.id")
    List<Master> getMastersByCarStatus(Car.Status status);
}
