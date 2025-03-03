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

    public List<Car> getCar(int count, String sortBy) {
        if (sortBy != null && !carProperties.getEnabledFields().contains(sortBy)) {
            return List.of();
        }

        Sort sort;

        if (sortBy != null) {
            sort = Sort.by(sortBy);
        } else {
            sort = Sort.unsorted();
        }

        int limit;
        if (count > 0 && count <= carProperties.getMax()) {
            limit = count;
        } else {
            limit = Integer.MAX_VALUE;
        }

        Pageable pageable = PageRequest.of(0, limit, sort);
        System.out.println(carRepository.findAll(pageable).getContent());
        return carRepository.findAll(pageable).getContent();
    }
}