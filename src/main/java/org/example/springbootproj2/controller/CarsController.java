package org.example.springbootproj2.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootproj2.DTO.CarDto;
import org.example.springbootproj2.model.Car;
import org.example.springbootproj2.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarsController {

    private final CarService carService;

    @GetMapping
    public List<Car> getCars(@RequestParam(required = false) Integer count,
                             @RequestParam(required = false) String sortBy) {
        List<Car> cars;

        if (count != null && sortBy != null) {
            cars = carService.getCarByQuantitySort(count, sortBy);
        } else if (sortBy != null) {
            cars = carService.getCarBySort(sortBy);
        } else if (count != null) {
            cars = carService.getCarByQuantity(count);
        } else {
            cars = carService.getAllCar();
        }

        if (cars.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Сортировка по данному параметру заблокированна");
        }

        return cars;
    }

    @PostMapping
    public Car createCar(@RequestBody CarDto carDto) {
        return carService.addCar(carDto);
    }
}
