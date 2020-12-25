package com.delivery.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RestaurantRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 200)
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 500)
    private String address;

    @NotNull(message = "Rating is required")
    private Double rating;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
