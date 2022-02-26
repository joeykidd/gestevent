package com.kumojin.gestevent.web.error;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ErrorAttributes {
    private int status;
    private String message;
    private Instant timestamp;
    private List<String> fieldErrors;
}
