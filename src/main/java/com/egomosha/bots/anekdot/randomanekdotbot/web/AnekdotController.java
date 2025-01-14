package com.egomosha.bots.anekdot.randomanekdotbot.web;

import com.egomosha.bots.anekdot.randomanekdotbot.Anekdot;
import com.egomosha.bots.anekdot.randomanekdotbot.Repository.AnekdotRepository;
import com.egomosha.bots.anekdot.randomanekdotbot.bot.AnekdotBot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnekdotController {

    private final AnekdotRepository anekdotRepository;

    public AnekdotController(final AnekdotRepository anekdotRepository) {
        this.anekdotRepository = anekdotRepository;
    }

    @GetMapping("/random-anekdot")
    public Anekdot getRandomAnekdot() {
        return anekdotRepository.findRandomAnekdot();
    }
}
