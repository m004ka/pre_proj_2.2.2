package org.example.springbootproj2.service;


import lombok.RequiredArgsConstructor;
import org.example.springbootproj2.DTO.CarDto;
import org.example.springbootproj2.model.Car;
import org.example.springbootproj2.repository.CarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    @Value("${size.max}")
    private int maxCar;

    @Value("${sorting.enabled-fields}")
    private String[] enabledSortFields;

    private final CarRepository carRepository;

    @Override
    public List<Car> getAllCar() {
        return carRepository.findAll();
    }


    private List<Car> sortCars(List<Car> cars, String sortBy) {

        if (!Arrays.asList(enabledSortFields).contains(sortBy)) {
            return List.of();
        }

        Comparator<Car> comparator;

        switch (sortBy.toLowerCase()) {
            case "year":
                comparator = Comparator.comparing(Car::getYear);
                break;
            case "brand":
                comparator = Comparator.comparing(Car::getBrand, String.CASE_INSENSITIVE_ORDER);
                break;
            case "model":
                comparator = Comparator.comparing(Car::getModel, String.CASE_INSENSITIVE_ORDER);
                break;
            default:
                return List.of();
        }

        return cars.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<Car> getCarByQuantitySort(int count, String sortBy) {
        if (count > maxCar) {
            return sortCars(getAllCar(), sortBy);
        } else {
            return getCarQuantity(sortCars(carRepository.findAll(), sortBy), count);
        }

    }

    private List<Car> getCarQuantity(List<Car> car, int count) {
        return car.stream().limit(count).collect(Collectors.toList());
    }

    @Override
    public List<Car> getCarBySort(String sortBy) {
        return sortCars(getAllCar(), sortBy);
    }

    public List<Car> getCarByQuantity(int count) {
        if (count > maxCar) {
            return getAllCar();
        } else {
            return carRepository.findCarsWithLimit(count);
        }

    }


    //    @PostConstruct
//    public void init() {
//        carRepository.save(Car.builder()
//                .year(2024)
//                .brand("BMW")
//                .model("5 series G30")
//                .build());
//    }
    public Car addCar(CarDto carDto) {
        Car car = Car.builder().model(carDto.getModel()).brand(carDto.getBrand()).year(carDto.getYear()).build();
        carRepository.save(car);

        return car;
    }
}
