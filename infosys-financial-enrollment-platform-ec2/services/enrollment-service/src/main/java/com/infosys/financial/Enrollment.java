package com.infosys.financial;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String processingId;
    
    private String customerId;
    private String accountId;
    private String currentTier;
    private String newTier;
    private String action;
    
    // Default constructor
    public Enrollment() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProcessingId() {
        return processingId;
    }
    
    public void setProcessingId(String processingId) {
        this.processingId = processingId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public String getCurrentTier() {
        return currentTier;
    }
    
    public void setCurrentTier(String currentTier) {
        this.currentTier = currentTier;
    }
    
    public String getNewTier() {
        return newTier;
    }
    
    public void setNewTier(String newTier) {
        this.newTier = newTier;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
}
