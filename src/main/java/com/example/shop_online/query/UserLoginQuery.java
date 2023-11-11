package com.example.shop_online.query;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginQuery {
    @NotBlank(message = "code不能为空")
    private String code;
}