package com.cykj.dto;

import lombok.Data;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 响应数据传输对象
 */
@Data
public class ResponseDto {
    private int code;
    private String message;
    public Object data;

    public ResponseDto() {
    }

    public ResponseDto(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseDto success(String message, Object data) {
        return new ResponseDto(1, message, data);
    }

    public static ResponseDto fail(String message) {
        return new ResponseDto(-1, message, null);
    }
}
