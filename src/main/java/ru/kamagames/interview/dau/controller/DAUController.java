package ru.kamagames.interview.dau.controller;

import org.springframework.web.bind.annotation.*;
import ru.kamagames.interview.dau.controller.dto.ClickEventDTO;
import ru.kamagames.interview.dau.controller.dto.DAUResponseDTO;
import ru.kamagames.interview.dau.service.click.ClickService;

@RestController
public class DAUController {

    private final ClickService clickService;

    public DAUController(ClickService clickService) {
        this.clickService = clickService;
    }

    @PostMapping("/event")
    public void postEvent(@RequestBody ClickEventDTO event) {
        clickService.recordEvent(event);
    }

    @GetMapping("/dau")
    public DAUResponseDTO getDAU(@RequestParam int[] authorsList) {
        int[] uniqueUsersCount = clickService.getUniqueUsersCount(authorsList);
        return new DAUResponseDTO(uniqueUsersCount);
    }
}
