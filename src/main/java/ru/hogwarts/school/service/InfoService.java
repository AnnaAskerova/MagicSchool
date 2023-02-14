package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {
    @Value("${server.port}")
    private int port;
    private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

    public InfoService() {
    }

    public int getPort() {
        logger.debug("Вызван метод getPort");
        return port;
    }

    public int getSum() {
        logger.debug("Вызван метод getSum");
        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000_00)
                .mapToInt(Integer::intValue).sum();
    }
}
