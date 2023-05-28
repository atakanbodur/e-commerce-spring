package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    List<Campaign> findCampaignByProductId(int productId);

    List<Campaign> findTop10ByOrderByProductId();

    Campaign save(Campaign campaign);

    List<Campaign> getCampaignsByStoreid(int id);

    void deleteByCampaignId(int campaignId);
    }

