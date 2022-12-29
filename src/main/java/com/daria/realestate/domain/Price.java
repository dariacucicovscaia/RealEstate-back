package com.daria.realestate.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Price {
    private Long id;
    private Long price;
    private LocalDateTime lastUpdatedAt;
    private String currency;

    private Estate estate;

    public Price() {
    }

    public Price(Long id, Long price, LocalDateTime lastUpdatedAt, String currency) {
        this.id = id;
        this.price = price;
        this.lastUpdatedAt = lastUpdatedAt;
        this.currency = currency;
    }

    public Price(Long price, LocalDateTime lastUpdatedAt, String currency, Estate estate) {
        this.price = price;
        this.lastUpdatedAt = lastUpdatedAt;
        this.currency = currency;
        this.estate = estate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price=" + price +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", currency='" + currency + '\'' +
                ", estate=" + estate +
                '}';
    }
}
