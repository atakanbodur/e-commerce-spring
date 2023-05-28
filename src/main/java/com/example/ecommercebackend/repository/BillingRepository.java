package com.example.ecommercebackend.repository;
import com.example.ecommercebackend.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BillingRepository extends JpaRepository <Billing, Integer> {
    Billing save(Billing billing);
    Billing findByBillingid(Billing id);
}
