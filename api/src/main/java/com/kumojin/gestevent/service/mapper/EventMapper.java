package com.kumojin.gestevent.service.mapper;

import org.mapstruct.Mapper;

import com.kumojin.gestevent.domain.Event;
import com.kumojin.gestevent.service.dto.EventDto;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventDto eventDto);
    EventDto toDto(Event event);
}
