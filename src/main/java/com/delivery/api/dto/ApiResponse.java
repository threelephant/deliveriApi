package com.delivery.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Standard API response wrapper")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Schema(description = "Whether the request succeeded", example = "true")
    private boolean success;
    @Schema(description = "Optional message")
    private String message;
    @Schema(description = "Response payload")
    private T data;
    private Instant timestamp = Instant.now();

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data, Instant timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = true;
        r.data = data;
        return r;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = true;
        r.message = message;
        r.data = data;
        return r;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> r = new ApiResponse<>();
        r.success = false;
        r.message = message;
        return r;
    }

    public static <T> ApiResponse<T> builder() {
        return new ApiResponse<>();
    }

    public ApiResponse<T> success(boolean s) { this.success = s; return this; }
    public ApiResponse<T> message(String m) { this.message = m; return this; }
    public ApiResponse<T> data(T d) { this.data = d; return this; }
    public ApiResponse<T> timestamp(Instant t) { this.timestamp = t; return this; }
    public ApiResponse<T> build() { return this; }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public Instant getTimestamp() { return timestamp; }
}
