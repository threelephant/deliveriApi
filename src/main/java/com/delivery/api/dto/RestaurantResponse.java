package com.delivery.api.dto;

public class RestaurantResponse {

    private Long id;
    private String name;
    private String address;
    private Double rating;
    private Long ownerId;

    public RestaurantResponse() {
    }

    public RestaurantResponse(Long id, String name, String address, Double rating, Long ownerId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.ownerId = ownerId;
    }

    public static RestaurantResponseBuilder builder() {
        return new RestaurantResponseBuilder();
    }

    public static class RestaurantResponseBuilder {
        private Long id;
        private String name;
        private String address;
        private Double rating;
        private Long ownerId;

        public RestaurantResponseBuilder id(Long id) { this.id = id; return this; }
        public RestaurantResponseBuilder name(String name) { this.name = name; return this; }
        public RestaurantResponseBuilder address(String address) { this.address = address; return this; }
        public RestaurantResponseBuilder rating(Double rating) { this.rating = rating; return this; }
        public RestaurantResponseBuilder ownerId(Long ownerId) { this.ownerId = ownerId; return this; }

        public RestaurantResponse build() {
            return new RestaurantResponse(id, name, address, rating, ownerId);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
}
