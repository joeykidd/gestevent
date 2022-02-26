package com.kumojin.gestevent.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kumojin.gestevent.service.dto.EventDto;

public interface EventService {
    /**
     * create new event
     * @param eventDto
     * @return eventDto
     */
    EventDto createEvent(EventDto eventDto);

    /**
     * get page of events
     * @param pageable
     * @return Page<EventDto>
     */
    Page<EventDto> getEvents(Pageable pageable);
}
