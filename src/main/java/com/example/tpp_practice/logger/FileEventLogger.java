package com.example.tpp_practice.logger;

import com.example.tpp_practice.logger.Event.Event;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileEventLogger extends AbstractLogger {
    private File file;

    private final String filename;

    public FileEventLogger(String filename) {
        this.filename = filename;
    }

    @PostConstruct
    public void init() throws IOException {
        this.file = new File(filename);
        if(file.exists() && !file.canWrite()) {
            throw new IllegalArgumentException("Can't write to file" + filename);
        }
        else {
            file.createNewFile();
        }
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.toString() + "\n", StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
