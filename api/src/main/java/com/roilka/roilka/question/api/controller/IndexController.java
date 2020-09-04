package com.roilka.roilka.question.api.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class IndexController {

    public String index() {
        int clientCount = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
        ExecutorService executorService = Executors.newFixedThreadPool(clientCount);
        long start = System.currentTimeMillis();

        return "ss";
    }
}
