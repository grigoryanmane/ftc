package aca.project.ftc.model.dto.request.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddDto {

    private Long productId;
    private Long userId;
    private String description;
    private Double amount;
    private Double quantity;
}
