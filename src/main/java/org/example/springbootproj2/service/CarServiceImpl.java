package org.example.springbootproj2.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootproj2.config.CarProperties;
import org.example.springbootproj2.model.Car;
import org.example.springbootproj2.repository.CarRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarProperties carProperties;

    public List<Car> getCarForParam(int count, String sortBy) {

        if (sortBy != null && !carProperties.getEnabledFields().contains(sortBy)) {
            return List.of();
        }

        if (count > 0 && sortBy != null) {
            return getCarByQuantitySort(count, sortBy);
        }

        if (sortBy != null) {
            return getCarBySort(sortBy);
        }

        if (count > 0) {
            return getCarByQuantity(count);
        }

        return getAllCar();
    }

    @Override
    public List<Car> getAllCar() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> getCarByQuantitySort(int count, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();

        if (count > carProperties.getMax()) {
            return carRepository.findAll(sort);
        }
        Pageable pageable = PageRequest.of(0, count, Sort.by(sortBy));
        return carRepository.findAllWithLimitAndSort(pageable);
    }

    @Override
    public List<Car> getCarBySort(String sortBy) {
        return carRepository.findAll(Sort.by(sortBy).ascending());
    }

    @Override
    public List<Car> getCarByQuantity(int count) {
        if (count > carProperties.getMax()) {
            return getAllCar();
        }

        return carRepository.findCarsWithLimit(count);
    }
}
