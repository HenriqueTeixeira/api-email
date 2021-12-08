package com.hlrconsult.apiemail.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorObject {

    private final String message;
    private final String field;
    private final Object parameter;
}