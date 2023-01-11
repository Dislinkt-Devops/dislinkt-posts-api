package com.dislinkt.post.dto;

public class ResponseDTO<T> {
    
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseDTO() {
    }

    public ResponseDTO(T data) {
        this.data = data;
    }
    
 }
