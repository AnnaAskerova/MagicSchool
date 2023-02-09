package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    @Value("${server.port}")
    private int port;
    Logger logger = LoggerFactory.getLogger(InfoService.class);

    public InfoService() {
    }

    public int getPort() {
        logger.debug("Вызван метод getPort");
        return port;
    }
}
