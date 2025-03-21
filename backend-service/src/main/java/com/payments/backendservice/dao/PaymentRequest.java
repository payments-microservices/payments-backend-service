package com.payments.backendservice.dao;

import com.payments.backendservice.models.Metadata;
import com.payments.backendservice.models.PaymentDetails;

public class PaymentRequest {
    private String id;
    private PaymentDetails payment_details;
    private Metadata metadata;

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentDetails getPayment_details() {
        return payment_details;
    }

    public void setPayment_details(PaymentDetails payment_details) {
        this.payment_details = payment_details;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "id='" + id + '\'' +
                ", payment_details=" + payment_details +
                ", metadata=" + metadata +
                '}';
    }
}
