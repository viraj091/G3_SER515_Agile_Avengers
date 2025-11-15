package com.scrumsim.model;

public enum UserRole {

    SCRUM_MASTER("Scrum Master"),    
    DEVELOPER("Developer"),          
    PRODUCT_OWNER("Product Owner");  

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}