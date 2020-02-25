package aca.project.ftc.model.dto.request.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    private Long id;
    private Long productId;
    private String description;
    private Double amount;
    private Double quantity;
}
