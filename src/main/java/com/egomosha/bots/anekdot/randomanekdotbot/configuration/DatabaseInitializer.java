package com.egomosha.bots.anekdot.randomanekdotbot.configuration;

import com.egomosha.bots.anekdot.randomanekdotbot.Anekdot;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import com.egomosha.bots.anekdot.randomanekdotbot.Repository.AnekdotRepository;


@Configuration
class DatabaseInitializer {

    @Autowired
    private AnekdotRepository anekdotRepository;

    @PostConstruct
    public void initDatabase(){
        anekdotRepository.save(new Anekdot("Я мало знаю о Швейцарии, но её флаг это уже большой плюс"));
        anekdotRepository.save(new Anekdot("Нервный альпинист время от времени срывается на свою жену."));
        anekdotRepository.save(new Anekdot("-Алло, это Чешская Республика? Почешите мне спинку."));
        anekdotRepository.save(new Anekdot("В чем разница между землей и шутками в моей базе данных? Земля не плоская."));
        anekdotRepository.save(new Anekdot("При вскрытии матрешки было обнаружено еще 7 трупов."));
        anekdotRepository.save(new Anekdot("Из полицейского протокола: «Бросал лебедям хлебные крошки. Сорвал балет «Лебединое озеро»."));
    }
}
