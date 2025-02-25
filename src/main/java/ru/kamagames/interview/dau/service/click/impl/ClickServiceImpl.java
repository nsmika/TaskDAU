package ru.kamagames.interview.dau.service.click.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kamagames.interview.dau.controller.dto.ClickEventDTO;
import ru.kamagames.interview.dau.repository.ClickRepository;
import ru.kamagames.interview.dau.service.click.ClickService;
import ru.kamagames.interview.dau.service.time.TimeService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class ClickServiceImpl implements ClickService {

    private final ClickRepository clickRepository;
    private final TimeService timeService;

    public ClickServiceImpl(ClickRepository clickRepository, TimeService timeService) {
        this.clickRepository = clickRepository;
        this.timeService = timeService;
    }

    @Override
    public void recordEvent(ClickEventDTO clickEventDTO) {
        // Фиксируем время события на сервере
        Instant now = Instant.now();
        clickRepository.recordClick(clickEventDTO.userId(), clickEventDTO.authorId(), now);
    }

    @Override
    public int[] getUniqueUsersCount(int[] authorsList) {
        Instant todayInstant = timeService.today();
        LocalDate previousDay = todayInstant.minus(1, ChronoUnit.DAYS).atZone(ZoneOffset.UTC).toLocalDate();

        int[] result = new int[authorsList.length];
        for (int i = 0; i < authorsList.length; i++) {
            result[i] = clickRepository.getUniqueUserCountForAuthorForDay(authorsList[i], previousDay);
        }
        return result;
    }

    // Периодическая очистка устаревших данных (раз в сутки)
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void clean() {
        // Остаются данные за предыдущий день и текущий день
        Instant todayInstant = timeService.today();
        LocalDate threshold = todayInstant.minus(1, ChronoUnit.DAYS).atZone(ZoneOffset.UTC).toLocalDate();
        clickRepository.cleanOldData(threshold);
    }
}
