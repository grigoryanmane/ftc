package aca.project.ftc.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProductResponseDto {

    private Long id;
    private Long userId;
    private Long productId;
    private Double amount;
    private Double quantity;
    private String description;
    private Boolean isActive;

}
