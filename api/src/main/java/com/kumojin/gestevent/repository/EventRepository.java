package com.kumojin.gestevent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kumojin.gestevent.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByName(String name);
}
