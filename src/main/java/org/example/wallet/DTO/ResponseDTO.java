package org.example.wallet.DTO;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private int statusCode;
    private T data;

    public ResponseDTO(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
