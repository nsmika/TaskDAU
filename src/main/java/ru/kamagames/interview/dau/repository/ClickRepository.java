package ru.kamagames.interview.dau.repository;

import java.time.Instant;
import java.time.LocalDate;

public interface ClickRepository {
    void recordClick(int userId, int authorId, Instant timestamp);
    int getUniqueUserCountForAuthorForDay(int authorId, LocalDate day);
    void cleanOldData(LocalDate threshold);
}
