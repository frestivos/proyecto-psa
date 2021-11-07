package com.aninfo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import static com.aninfo.model.TransactionStatus.FAILED;
import static com.aninfo.model.TransactionStatus.SUCCESSFUL;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long cbu;

    @NotNull
    private Long destinationCbu;

    @NotNull
    private TransactionType type;

    @NotNull
    private Double sum;

    @NotNull
    private TransactionStatus status = SUCCESSFUL;

    public Transaction() {
    }

    public Transaction(Long cbu, Long destinationCbu, TransactionType type, Double sum) {
        this.cbu = cbu;
        this.destinationCbu = destinationCbu;
        this.type = type;
        this.sum = sum;
    }

    public Transaction failed() {
        this.status = FAILED;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getDestinationCbu() {
        return destinationCbu;
    }

    public void setDestinationCbu(Long destinationCbu) {
        this.destinationCbu = destinationCbu;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
