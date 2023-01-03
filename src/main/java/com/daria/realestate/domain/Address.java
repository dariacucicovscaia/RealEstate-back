package com.daria.realestate.domain;

public class Address {
    private Long id;
    private String fullAddress;
    private String city;
    private String country;

    public Address() {
    }

    public Address(Long id, String fullAddress, String city, String country) {
        this.id = id;
        this.fullAddress = fullAddress;
        this.city = city;
        this.country = country;
    }

    public Address(String fullAddress, String city, String country) {
        this.fullAddress = fullAddress;
        this.city = city;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", fullAddress='" + fullAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
