package aca.project.ftc.model.dto.request.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterRequestDto {

    private Long productId;

    private String productName;

    private Boolean isActive;

    private Double price;

    private Double quantity;

}
