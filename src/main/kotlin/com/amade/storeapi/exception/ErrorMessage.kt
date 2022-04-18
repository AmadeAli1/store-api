package com.amade.storeapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ErrorMessage {
    private String message;
    private Integer requestStatus;
    private List<ErrorAtribute> erros;


    public record ErrorAtribute(String field, String message) {
    }
}
