package com.example.tpp_practice.logger;

import com.example.tpp_practice.logger.Event.Event;

public abstract class AbstractLogger implements EventLogger{

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
