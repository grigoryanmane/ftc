package aca.project.ftc.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProductRequest {

    private Long userId;
    private Long productId;
    private String description;
    private Double amount;
    private Double quantity;
}
