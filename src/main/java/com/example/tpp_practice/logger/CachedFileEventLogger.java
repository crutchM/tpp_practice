package com.example.tpp_practice.logger;

import com.example.tpp_practice.logger.Event.Event;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

public class CachedFileEventLogger  extends FileEventLogger{
    private final int cacheSize;
    private final List<Event> cache;

    public CachedFileEventLogger(String filename, int cacheSize) {
        super(filename);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<>(cacheSize);
    }

    @PreDestroy
    public void destroy() {
        if(!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);

        if(cache.size() >= cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void writeEventsFromCache() {
        this.cache.forEach(super::logEvent);
    }
}
