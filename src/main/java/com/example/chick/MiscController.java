package com.example.chick;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/")
public class MiscController{

    private AtomicInteger counter = new AtomicInteger();

    @GetMapping(produces = "application/json")
    public String greeting(){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson("{}", JsonObject.class);

        String message = "Hello, World! Counter: " + counter.getAndIncrement();
        jsonObject.addProperty("message", message);
        return gson.toJson(jsonObject);
    }
}