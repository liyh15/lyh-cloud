package wibo.cloud.custom.config;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUser {
    @NotBlank
    private String userId;
}
