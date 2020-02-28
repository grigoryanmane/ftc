package aca.project.ftc.model.dto.response.notification;

import aca.project.ftc.model.dto.response.product.ProductResponseDto;
import aca.project.ftc.model.dto.response.user.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponseDto {
    private Long id;
    private UserResponseDto sender;
    private UserResponseDto receiver;
    private ProductResponseDto product;
    private String message;
    private String status;
}
