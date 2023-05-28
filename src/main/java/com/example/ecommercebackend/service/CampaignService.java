package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.Campaign;
import com.example.ecommercebackend.model.Customer;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.User;
import com.example.ecommercebackend.repository.CampaignRepository;
import com.example.ecommercebackend.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomerRepository customerRepository;

    public Campaign insertCampaign(Campaign campaign) {
        List<Customer> customers = customerRepository.findAll();
        for (Customer c : customers) {
            emailService.sendEmail(c.getEmail(),"New Campaign", "Here's a new campaign for you:" +
                    campaign.toString());
        }

        return campaignRepository.save(campaign);
    }

    public Optional<Campaign> getCampaign(Integer campaignId) {
        return campaignRepository.findById(campaignId);
    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign updateCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public List<Campaign> getRecommendedCampaigns() { return campaignRepository.findTop10ByOrderByProductId(); }

    public Campaign editCampaign(Integer id, Campaign updatedCampaign) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Campaign not found with id: " + id));
        campaign.setProductId(updatedCampaign.getProductId());
        campaign.setStoreid(updatedCampaign.getStoreid());
        campaign.setDiscountAmount(updatedCampaign.getDiscountAmount());
        campaign.setDiscountType(updatedCampaign.getDiscountType());
        campaign.setStartDate(updatedCampaign.getStartDate());
        campaign.setEndDate(updatedCampaign.getEndDate());
        return campaignRepository.save(campaign);
    }

    public void deleteCampaign(Integer campaignId) {
        campaignRepository.deleteById(campaignId);
    }

    public Optional<Campaign> getCampaignById(int campaignId) { return campaignRepository.findById(campaignId);
    }

}
