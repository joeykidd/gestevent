package com.kumojin.gestevent.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kumojin.gestevent.service.EventService;
import com.kumojin.gestevent.service.dto.EventDto;
import com.kumojin.gestevent.web.error.BadRequestException;

import lombok.AllArgsConstructor;

@CrossOrigin("http://localhost:3000") // for the sake of testing...
@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {
    private final static String baseUri = "/events/";
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventDto>> getEvents(Pageable pageable) {
        Page<EventDto> eventDtos = eventService.getEvents(pageable);
        return ResponseEntity.ok(eventDtos);
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody @Valid EventDto newEventDto) {
        if (newEventDto.getId() != null) {
            throw new BadRequestException("A new event cannot have an ID");
        }
        EventDto eventDto = eventService.createEvent(newEventDto);
        return ResponseEntity.created(URI.create(baseUri + eventDto.getId())).body(eventDto);
    }
}
