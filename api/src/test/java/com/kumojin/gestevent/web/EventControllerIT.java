package com.kumojin.gestevent.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kumojin.gestevent.domain.Event;
import com.kumojin.gestevent.repository.EventRepository;
import com.kumojin.gestevent.service.EventService;
import com.kumojin.gestevent.service.dto.EventDto;
import com.kumojin.gestevent.utils.TestUtils;

public class EventControllerIT extends BaseIT {
    private static final String PATH = "/events";

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEvents() throws Exception {
        Pageable pageable = PageRequest.of(1, 1);
        Page<EventDto> eventPage = eventService.getEvents(pageable);

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(eventPage);

        mockMvc.perform(
                get(PATH)
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        System.out.println(json);
    }

    @Test
    public void testCreateEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();
        EventDto eventDto = TestUtils.buildEventDto();

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        byte[] bytes = ow.writeValueAsBytes(eventDto);

        mockMvc.perform(
                post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isCreated());

        List<Event> eventList = eventRepository.findAll();
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        assertThat(testEvent.getDescription()).isEqualTo(eventDto.getDescription());
        assertThat(testEvent.getName()).isEqualTo(eventDto.getName());
        assertThat(testEvent.getStartDate()).isEqualTo(eventDto.getStartDate());
        assertThat(testEvent.getEndDate()).isEqualTo(eventDto.getEndDate());
    }

    @Test
    public void testCreateEventShouldReturn400WhenIdExistsInPayload() throws Exception {
        EventDto eventDto = TestUtils.buildEventDto(1L);

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        byte[] bytes = ow.writeValueAsBytes(eventDto);

        mockMvc.perform(
                post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isBadRequest());
        // could also test error output...
    }

    @Test
    public void testCreateEventShouldReturn400WhenNameAlreadyExists() throws Exception {
        // same logic as above
    }

    // timezone tests...

}
