package org.example.springbootproj2.service;

import org.example.springbootproj2.model.Car;

import java.util.List;

public interface CarService {
    List<Car> getCarForParam(int count, String sortBy);

    List<Car> getCarBySort(String sortBy);

    List<Car> getCarByQuantity(int count);

    List<Car> getCarByQuantitySort(int count, String sortBy);

    List<Car> getAllCar();
}
