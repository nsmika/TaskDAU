package ru.kamagames.interview.dau.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.kamagames.interview.dau.repository.ClickRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ClickRepositoryImpl implements ClickRepository{
    private static final Logger logger = LoggerFactory.getLogger(ClickRepositoryImpl.class);

    // Структура: ключ – UTC-день, значение – карта (ключ: authorId, значение: набор userId)
    private final ConcurrentHashMap<LocalDate, ConcurrentHashMap<Integer, Set<Integer>>> data = new ConcurrentHashMap<>();

    @Override
    public void recordClick(int userId, int authorId, Instant timestamp) {
        LocalDate day = timestamp.atZone(ZoneOffset.UTC).toLocalDate();
        data.computeIfAbsent(day, d -> new ConcurrentHashMap<>())
                .computeIfAbsent(authorId, a -> ConcurrentHashMap.newKeySet())
                .add(userId);
    }

    @Override
    public int getUniqueUserCountForAuthorForDay(int authorId, LocalDate day) {
        var dayData = data.get(day);
        if (dayData == null) {
            return 0;
        }
        var users = dayData.get(authorId);
        return (users != null) ? users.size() : 0;
    }

    @Override
    public void cleanOldData(LocalDate threshold) {
        data.keySet().removeIf(day -> day.isBefore(threshold));
        logger.info("Очистка данных: удалены записи до {}", threshold);
    }
}
