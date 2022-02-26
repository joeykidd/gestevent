package com.kumojin.gestevent.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kumojin.gestevent.domain.Event;
import com.kumojin.gestevent.repository.EventRepository;
import com.kumojin.gestevent.service.NameAlreadyUsedException;
import com.kumojin.gestevent.service.dto.EventDto;
import com.kumojin.gestevent.service.mapper.EventMapper;
import com.kumojin.gestevent.utils.TestUtils;

public class EventServiceImplTest {
    @InjectMocks
    private EventServiceImpl eventService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventMapper eventMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEvent() {
        Event event = TestUtils.buildEvent(1L);
        EventDto eventDto = TestUtils.buildEventDto(1L);

        Mockito.when(eventMapper.toDto(event)).thenReturn(eventDto);
        Mockito.when(eventMapper.toEntity(eventDto)).thenReturn(event);
        Mockito.when(eventRepository.save(event)).thenReturn(event);

        EventDto actualEventDto = eventService.createEvent(eventDto);
        assertThat(actualEventDto).isSameAs(eventDto);
    }

    @Test
    void testCreateEventShouldThrowNameAlreadyUsedException() {
        Event event = TestUtils.buildEvent(1L);
        EventDto eventDto = TestUtils.buildEventDto();

        Mockito.when(eventRepository.findByName(any())).thenReturn(Optional.of(event));
        assertThrows(NameAlreadyUsedException.class, () -> eventService.createEvent(eventDto));
    }

    @Test
    void testGetEvents() {
        Page<Event> eventPage = TestUtils.buildEvents();
        Page<EventDto> eventDtoPage = TestUtils.buildEventDtos();

        eventPage.forEach(event ->
                Mockito.when(eventMapper.toDto(event))
                        .thenReturn(eventDtoPage.stream().filter(eventDto -> event.getId() == eventDto.getId()).findFirst().get()));
        Mockito.when(eventRepository.findAll(any(Pageable.class))).thenReturn(eventPage);

        Page<EventDto> actualEventDtos = eventService.getEvents(Mockito.mock(Pageable.class));

        assertThat(actualEventDtos.getTotalPages()).isEqualTo(eventDtoPage.getTotalPages());
        assertThat(actualEventDtos.getTotalElements()).isEqualTo(eventDtoPage.getTotalElements());
        assertThat(actualEventDtos.getContent()).isEqualTo(eventDtoPage.getContent());
    }
}
