package com.example.tpp_practice.logger;

import com.example.tpp_practice.logger.Event.Event;

public interface EventLogger {

    void logEvent(Event event);

    String getName();

}