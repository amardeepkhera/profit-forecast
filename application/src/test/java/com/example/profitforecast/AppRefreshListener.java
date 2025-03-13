package com.example.profitforecast;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AppRefreshListener {

    @Value("${app.maxAllowedContextRefreshCount}")
    private Integer maxAllowedContextRefreshCount;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @EventListener(value = {ContextRefreshedEvent.class})
    void onContextRefresh() {
        var refreshCount = COUNTER.addAndGet(1);
        System.out.println("Application context refresh count=" + refreshCount);
        if (refreshCount > maxAllowedContextRefreshCount) {
            throw new RuntimeException("Application context refresh count exceeded " + maxAllowedContextRefreshCount);
        }
    }


}
