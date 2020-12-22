package com.delivery.api.dto;

import com.delivery.api.enums.Role;

public class AuthResponse {

    private String token;
    private String type;
    private Long userId;
    private String email;
    private Role role;
    private String typeValue = "Bearer";

    public AuthResponse() {
    }

    public AuthResponse(String token, String type, Long userId, String email, Role role, String typeValue) {
        this.token = token;
        this.type = type;
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.typeValue = typeValue != null ? typeValue : "Bearer";
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String type;
        private Long userId;
        private String email;
        private Role role;
        private String typeValue = "Bearer";

        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder type(String type) { this.type = type; return this; }
        public AuthResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder role(Role role) { this.role = role; return this; }
        public AuthResponseBuilder typeValue(String typeValue) { this.typeValue = typeValue; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, type, userId, email, role, typeValue);
        }
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getTypeValue() { return typeValue; }
    public void setTypeValue(String typeValue) { this.typeValue = typeValue; }
}
