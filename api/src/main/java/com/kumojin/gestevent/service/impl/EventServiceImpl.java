package com.kumojin.gestevent.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kumojin.gestevent.domain.Event;
import com.kumojin.gestevent.repository.EventRepository;
import com.kumojin.gestevent.service.EventService;
import com.kumojin.gestevent.service.NameAlreadyUsedException;
import com.kumojin.gestevent.service.dto.EventDto;
import com.kumojin.gestevent.service.mapper.EventMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventDto createEvent(EventDto eventDto) {
        log.debug("request to save new event");
        eventRepository.findByName(eventDto.getName())
                .ifPresent(event -> {
                    throw new NameAlreadyUsedException();
                });
        Event event = eventMapper.toEntity(eventDto);
        event.setZoneId(event.getStartDate().getZone().getId());
        eventRepository.save(event);
        log.info("event with name {} was saved", event.getName());
        return eventMapper.toDto(event);
    }

    @Override
    public Page<EventDto> getEvents(Pageable pageable) {
        log.debug("request to get events {}", pageable);
        Page<Event> eventPage = eventRepository.findAll(pageable);
        List<EventDto> eventList = eventPage
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(eventList, pageable, eventPage.getTotalElements());
    }
}
