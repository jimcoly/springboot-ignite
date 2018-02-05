package com.oge.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oge.ignite.model.Car;
import com.oge.ignite.model.Master;

/**
 * @author Aliaksandr_Pleski
 * @since 8/2/2017.
 */
@Configuration
@EnableIgniteRepositories(basePackages = "com.oge.ignite.repository")
public class SpringAppConfig {
    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        // Setting some custom name for the node.
        cfg.setIgniteInstanceName("springDataNode");
        // Enabling peer-class loading feature.
        cfg.setPeerClassLoadingEnabled(true);
        // Defining and creating a new cache to be used by Ignite Spring Data
        // repository.
        CacheConfiguration ccfgCar = new CacheConfiguration("CarCache");
        CacheConfiguration ccfgMaster = new CacheConfiguration("MasterCache");
        // Setting SQL schema for the cache.
        ccfgMaster.setIndexedTypes(Long.class, Master.class);
        ccfgCar.setIndexedTypes(Long.class, Car.class);
        cfg.setCacheConfiguration(new CacheConfiguration[]{
                ccfgCar,
                ccfgMaster
        });
        return Ignition.start(cfg);
    }
}
