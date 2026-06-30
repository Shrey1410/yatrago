package com.yatrago.backend.common;

public class CommonEnums {

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum VehicleType {
        TWO_WHEELER, THREE_WHEELER, FOUR_WHEELER
    }

    public enum RideStatus {
        CONFIRMED, STARTED, COMPLETED, CANCELLED, BOOKED
    }

    public enum PaymentMethod {

    }

    public enum PaymentStatus {

    }

    public enum VehicleDocumentType {

    }

    public enum DriverDocumentType {

    }

    public enum DocumentVerificationStatus {
        
    }

    /**
     * Currently only have two flows of social Login Google auth and Facebook auth
     * LoginProvider
     */
    public enum SocialProvider {
        GOOGLE, FACEBOOK
    }
}
