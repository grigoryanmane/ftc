package aca.project.ftc.model.dto.request.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    //TODO:: REMOVE ID AND SEND IT VIA PATH FOR ADD PRODUCT, MAKE THIS THE ONLY REQUEST TYPE OTHER THAN FILTER
    private Long id;
    private String description;
    private Double amount;
    private Double quantity;
}
