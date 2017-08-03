package com.epam.jmp.ignite.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * @author Aliaksandr_Pleski
 * @since 8/2/2017.
 */
public class Car implements Serializable {
    @QuerySqlField(index = true)
    private long id;
    @QuerySqlField(index = true)
    private String name;
    @QuerySqlField(index = true)
    private int mileage;
    @QuerySqlField(index = true)
    private long masterId;
    @QuerySqlField(index = true)
    private Status status;

    public Car() {
    }

    public Car(long id, String name, int mileage, long masterId, Status status) {
        this.id = id;
        this.name = name;
        this.mileage = mileage;
        this.masterId = masterId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mileage=" + mileage +
                ", masterId=" + masterId +
                ", status=" + status +
                '}';
    }

    public enum Status {
        NOT_STARTED, REGISTERED, IN_PROGRESS, COMPLETE;

        public static Status getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

}
