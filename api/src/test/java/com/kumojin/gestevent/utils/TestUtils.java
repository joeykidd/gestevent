package com.kumojin.gestevent.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.kumojin.gestevent.domain.Event;
import com.kumojin.gestevent.service.dto.EventDto;

public class TestUtils {
    public static Event buildEvent(long id) {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 02, 22, 22, 22);
        return Event.builder()
                .id(id)
                .name("name")
                .description("description")
                .startDate(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()))
                .endDate(ZonedDateTime.of(localDateTime.plusDays(5), ZoneId.systemDefault()))
                .zoneId(ZoneId.systemDefault().getId())
                .build();
    }

    public static EventDto buildEventDto(long id) {
        EventDto eventDto = buildEventDto();
        eventDto.setId(id);
        return eventDto;
    }

    public static EventDto buildEventDto() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 02, 22, 22, 22);
        return EventDto.builder()
                .name("name")
                .description("description")
                .startDate(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()))
                .endDate(ZonedDateTime.of(localDateTime.plusDays(5), ZoneId.systemDefault()))
                .build();
    }

    public static Page<EventDto> buildEventDtos() {
        EventDto eventDto1 = buildEventDto(1L);
        EventDto eventDto2 = buildEventDto(2L);
        List<EventDto> eventDtoList = Arrays.asList(eventDto1, eventDto2);
        return new PageImpl<>(eventDtoList, Mockito.mock(Pageable.class), eventDtoList.size());
    }

    public static Page<Event> buildEvents() {
        Event event1 = buildEvent(1L);
        Event event2 = buildEvent(2L);
        List<Event> eventList = Arrays.asList(event1, event2);
        return new PageImpl<>(eventList, Mockito.mock(Pageable.class), eventList.size());
    }
}
