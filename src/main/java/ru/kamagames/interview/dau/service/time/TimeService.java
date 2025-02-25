package ru.kamagames.interview.dau.service.time;

import java.time.Instant;

public interface TimeService {
    // Возвращает Instant на начало текущих суток
    Instant today();
}
