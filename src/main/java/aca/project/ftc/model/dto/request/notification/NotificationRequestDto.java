package aca.project.ftc.model.dto.request.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDto {

    private Long senderId;
    private Long receiverId;
    private Long userProductId;
    private String message;


}
