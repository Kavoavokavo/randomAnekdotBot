package com.egomosha.bots.anekdot.randomanekdotbot.Repository;

import com.egomosha.bots.anekdot.randomanekdotbot.Anekdot;
import com.fasterxml.jackson.databind.introspect.Annotated;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Random;

public interface AnekdotRepository extends JpaRepository<Anekdot, Long> {

    default Anekdot findRandomAnekdot() {
        Random random = new Random();
        long count = count();

        if (count > 0) {
            int index = random.nextInt((int) count);
            Long intIndex = Long.valueOf(index+1);
            return findById(intIndex).orElse(null);
        } else {
            return null;
        }
    }

    default Anekdot findAnekdotById(Long id) {
        return findById(id).orElse(null);
    }

    default List<Anekdot> allAnekdots() {
        return findAll();
    }

    default void addAnekdot(String anekdotContent) {
        Anekdot anekdot = new Anekdot(anekdotContent);
        save(anekdot);
        System.out.println("Анекдот добавлен в базу данных");
    }
}
