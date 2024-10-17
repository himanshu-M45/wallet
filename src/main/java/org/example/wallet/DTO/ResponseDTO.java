package org.example.wallet.DTO;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private int statusCode;
    private String message;
    private T data;

    public ResponseDTO(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
