package org.example.springbootproj2.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootproj2.DTO.CarDto;
import org.example.springbootproj2.model.Car;
import org.example.springbootproj2.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public String getCars(@RequestParam(required = false) Integer count,
                          @RequestParam(required = false) String sortBy,
                          Model model) {
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Сортировка по данному параметру заблокированна или не существует");
        }
        model.addAttribute("cars", cars);
        return "cars";
    }

    @GetMapping("/create")
    public String showCreateCarForm(Model model) {
        model.addAttribute("carDto", new CarDto());
        return "create-car";
    }

    @PostMapping("/create")
    public String createCar(@ModelAttribute CarDto carDto) {
        carService.addCar(carDto);
        return "redirect:/cars";
    }
}
