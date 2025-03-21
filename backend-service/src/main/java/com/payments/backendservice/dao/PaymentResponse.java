package com.payments.backendservice.dao;

public class PaymentResponse {
    private String id;
    private String status;

    public PaymentResponse(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String  toString() {
        return "PaymentResponse{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
// getters and setters
}
