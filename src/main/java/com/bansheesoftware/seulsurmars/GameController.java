package com.bansheesoftware.seulsurmars;

import com.bansheesoftware.seulsurmars.domain.Monde;
import com.bansheesoftware.seulsurmars.services.CreerMondeService;
import com.bansheesoftware.seulsurmars.services.InputService;
import com.bansheesoftware.seulsurmars.services.TimerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("game")
public class GameController {
    Map<Integer, Monde> mondes = new ConcurrentHashMap<>();

    @Autowired
    private CreerMondeService creerMondeService;
    @Autowired
    private InputService inputService;
    @Autowired
    private TimerService timerService;
    
    public GameController() {
    }
    
    public enum Touche {
        LEFT, RIGHT, DECOR, OBJET,
    }

    @PostMapping(value = "touche")
    public Monde action(@RequestBody Map<String, String> body) {
        String key = body.get("touche");
        int id = Integer.valueOf(body.get("id"));
        Monde monde = mondes.get(id);
//        System.out.println(monde);
//        InputService inputService = new InputService();
        this.inputService.handleInput(monde, key);
        return monde;
    }

    @PostMapping(value = "timer")
    public Monde timer(@RequestBody Map<String, String> body) {
        String timer = body.get("timer");
        int id = Integer.valueOf(body.get("id"));
        Monde monde = mondes.get(id);
        if (timer == null) {
            return monde;
        }
//        System.out.println(timer);
//        System.out.println(body);
//        TimerService timerService = new TimerService();
        this.timerService.handleTime(monde, timer);
        return monde;
    }

    @GetMapping
    public Monde init() {
        Monde monde = creerMondeService.creerMondeTest();
//        monde.timerNourriture = 10;
        mondes.put(monde.getId(), monde);
        return monde;
    }
}