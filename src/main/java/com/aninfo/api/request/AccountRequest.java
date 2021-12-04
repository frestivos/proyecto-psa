package com.aninfo.api.request;

public class AccountRequest {
    private Long cbu;
    private String alias;
    private String currency;
    private String name;

    public Long getCbu() {
        return cbu;
    }

    public String getAlias() {
        return alias;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }
}
