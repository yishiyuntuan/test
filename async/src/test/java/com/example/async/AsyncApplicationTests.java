package com.example.async;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class AsyncApplicationTests {

    @Test
    void contextLoads() {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("洗水壶");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return "水壶";
        }).thenApply(e->{
            System.out.print(e);
            System.out.println("=====>烧水");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return "热水";
        });
        //洗水壶->洗水杯->拿茶叶
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("洗茶壶");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return "茶壶";
        }).thenApply(e->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.print(e);
            System.out.println("洗水杯");
            return "水杯";
        }).thenApply(e->{
            System.out.print(e);
            System.out.println("拿茶叶");
            return "茶叶";
        });
        //泡茶
        CompletableFuture<String> task3 = task1.thenCombine(task2, (a, b) -> {
            System.out.println("泡茶");
            return "茶";
        });
        String tea = task3.join();
        System.out.println(tea);
    }

    @Test
    public void test()
    {

    }

}
