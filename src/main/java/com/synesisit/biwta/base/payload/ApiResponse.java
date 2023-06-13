package com.synesisit.biwta.base.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object data;
    private Long count;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public ApiResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Boolean success, String message, Object data, Long count) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.count = count;
    }

}
