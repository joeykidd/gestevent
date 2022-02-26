package com.kumojin.gestevent.service.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventDto {
    private Long id;
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 32, message = "Name should be between 1 and 32 characters")
    private String name;
    private String description;
    @NotNull(message = "Start date cannot be null")
    private ZonedDateTime startDate;
    @NotNull(message = "End date cannot be null")
    private ZonedDateTime endDate;
    // needs validation to prevent endDate < startDate
}
