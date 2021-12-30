package com.example.tpp_practice.logger.Event;

import java.text.DateFormat;
import java.util.Date;

public class Event {
    private EventType eventType = EventType.INFO;
    private String message = "!!!";
    private static int count= 0;
    private int id = 0;
    private Date date;

    private Event() {}

    private Event(EventType type) {
        this.eventType = type;
        id = count++;
    }

    public static Event level(EventType type) {
        return new Event(type);
    }

    public Event that(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "<" + eventType + "> [ id=" + id + ", date=" + (DateFormat.getDateTimeInstance()).format(date) + ", msg=" + message + " ]";
    }

    public Event at(Date date) {
        this.date = date;
        return this;
    }

    public Event now() {
        this.date = new Date();
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
