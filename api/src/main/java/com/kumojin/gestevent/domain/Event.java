package com.kumojin.gestevent.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GEN_EVENT_SEQ")
    @SequenceGenerator(name = "GEN_EVENT_SEQ", sequenceName = "SEQ_EVENT", allocationSize = 1)
    private Long id;
    @Column(length = 32, nullable = false, unique = true)
    private String name;
    private String description;
    @Column(nullable = false)
    private ZonedDateTime startDate;
    @Column(nullable = false)
    private ZonedDateTime endDate;
    private String zoneId;
}
