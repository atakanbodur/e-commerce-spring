package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByCustomerid(int id);

    @Query(value = "SELECT to_char(orderdate, 'YYYY-MM'), COUNT(orderid) " +
            "FROM orders WHERE orderdate BETWEEN :startDate AND :endDate " +
            "GROUP BY to_char(orderdate, 'YYYY-MM')", nativeQuery = true)
    List<Object[]> getOrdersPerMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
