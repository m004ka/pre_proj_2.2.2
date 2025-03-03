package org.example.springbootproj2.service;

import org.example.springbootproj2.model.Car;

import java.util.List;

public interface CarService {
    List<Car> getCar(int count, String sortBy);
}
