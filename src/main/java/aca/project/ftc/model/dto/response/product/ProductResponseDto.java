package aca.project.ftc.model.dto.response.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {

    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private String companyName;
    private Double amount;
    private Double quantity;
    private String description;
    private Boolean isActive;

}
