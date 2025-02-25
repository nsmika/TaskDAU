package ru.kamagames.interview.dau.service.click;

import ru.kamagames.interview.dau.controller.dto.ClickEventDTO;

public interface ClickService {
    void recordEvent(ClickEventDTO clickEventDTO);
    int[] getUniqueUsersCount(int[] authorsList);
}
