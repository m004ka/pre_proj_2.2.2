package org.example.springbootproj2.controller;

import lombok.RequiredArgsConstructor;
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
        if (count == null || count <= 0) {
            count = 0;
        }
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = null;
        }

        List<Car> cars = carService.getCarForParam(count, sortBy);

        if (cars.isEmpty() && sortBy != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Сортировка по данному параметру заблокирована или не существует");
        }

        model.addAttribute("cars", cars);
        return "cars";
    }

}
