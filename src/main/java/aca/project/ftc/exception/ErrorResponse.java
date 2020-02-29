package aca.project.ftc.exception;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder

public class ErrorResponse {
    private String errorCode;
    private String message;
    private List<ErrorParam> errorParamList;

}
