package ru.kamagames.interview.dau.service.time.impl;

import org.springframework.stereotype.Component;
import ru.kamagames.interview.dau.service.time.TimeService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TimeServiceImpl implements TimeService {
    @Override
    public Instant today() {
        return Instant.now().truncatedTo(ChronoUnit.DAYS);
    }
}
