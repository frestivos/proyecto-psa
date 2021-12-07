package com.aninfo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(name = "cbu", unique = true)
    private Long cbu;

    @NotNull
    @Column(name = "alias", unique = true)
    private String alias;

    @NotNull
    private Currency currency;

    @NotNull
    private String name;

    @NotNull
    private Double balance;

    public Account(){
    }

    public Account(Long cbu,
                   String alias,
                   Currency currency,
                   String name,
                   Double balance) {
        this.cbu = cbu;
        this.alias = alias;
        this.currency = currency;
        this.name = name;
        this.balance = balance;
    }

    public Account(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency(){
        return this.currency;
    }

    public void setCurrency(Currency newCurrency){
        this.currency = newCurrency;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public Long getId() {
        return this.id;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
