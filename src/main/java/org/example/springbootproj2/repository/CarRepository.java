package org.example.springbootproj2.repository;

import org.example.springbootproj2.model.Car;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "SELECT * FROM car ORDER BY brand LIMIT :count", nativeQuery = true)
    List<Car> findCarsWithLimit(@Param("count") int count);

    List<Car> findAll(Sort sort);

    @Query(value = "SELECT * FROM Car LIMIT ?1", nativeQuery = true)
    List<Car> findAllWithLimitAndSort(@Param("count") int count, Sort sort);

}
