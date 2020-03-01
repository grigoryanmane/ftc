package aca.project.ftc.model.dto.response.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductListResponseDto {
    private Long totalElements;
    private Integer pageCount;
    private List<ProductResponseDto> productResponseDtoList;

}
