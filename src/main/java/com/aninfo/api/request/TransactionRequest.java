package com.aninfo.api.request;

public class TransactionRequest {
    private Long destinationCbu;
    private String type;
    private Double sum;

    public Long getDestinationCbu() {
        return destinationCbu;
    }

    public String getType() {
        return type;
    }

    public Double getSum() {
        return sum;
    }
}
