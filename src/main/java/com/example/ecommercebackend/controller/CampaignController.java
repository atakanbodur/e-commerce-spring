package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.CampaignRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/campaign")
public class CampaignController {
    @Autowired
    CampaignService campaignService;
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    ProductRepository productRepository;


    @PostMapping("/insertCampaign/{id}")
    public Campaign insertCampaign(@RequestBody Campaign campaign, @PathVariable int id) {
        campaign.setStoreid(id);
        campaign.setProductName(productRepository.findByProductid(campaign.getProductId()).getName());
        return campaignService.insertCampaign(campaign);
    }

    @GetMapping("/campaigns")
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/campaigns/recommended")
    public List<Campaign> getRecommendedCampaigns() { return campaignService.getRecommendedCampaigns(); }

    @PutMapping("/editCampaign/{id}")
    public Campaign editCampaign(@PathVariable Integer id, @RequestBody Campaign updatedCampaign) {
        return campaignService.editCampaign(id, updatedCampaign);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Integer id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/campaigns/{campaignId}")
    public ResponseEntity<Optional<Campaign>> getCampaignByCampaignId(@PathVariable int campaignId) {


        Optional<Campaign> campaign = campaignService.getCampaignById(campaignId);
        if (campaign.isEmpty())  {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(campaign);
    }

    @GetMapping("/store/{id}")
    public List<Campaign> getCampaignsByStoreid(@PathVariable int id) {
        return campaignRepository.getCampaignsByStoreid(id);
    }



}