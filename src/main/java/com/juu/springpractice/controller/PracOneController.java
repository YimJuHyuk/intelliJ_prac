package com.juu.springpractice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@RestController("/prac")
@Slf4j
public class PracOneController {

   @PostConstruct
   public void init() throws Exception{
       log.debug("##### 병렬, 순차처리 속도 테스트 #####");
       fokeJoinPoolPrac();
       parallelPrac();
       sequentialPrac();
   }

   private void fokeJoinPoolPrac() throws Exception{
       int exponent = 6;

       Integer parallelism = (int) Math.pow(2, exponent);
       ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

       log.debug("[FokeParallelStream] 서버 구동시 실행 size => {}", setNum().size());
       long startTime = System.currentTimeMillis();
       forkJoinPool.submit(() -> {
           setNum().parallelStream().forEach(num -> {
               if(num % 1000 == 0){
                   try {
                       Thread.sleep(10);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
//               log.debug(num + "번 실행!!");
          });
       }).get();
       long endTime = System.currentTimeMillis();
       log.debug("[FokeParallelStream] 서버 구동시 종료 => 총 시간 : {}", endTime-startTime);
   }

    private void parallelPrac() throws Exception{
        log.debug("[ParallelStream] 서버 구동시 실행 size => {}", setNum().size());
        long startTime = System.currentTimeMillis();
            setNum().parallelStream().forEach(num -> {
                if(num % 1000 == 0){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
//               log.debug(num + "번 실행!!");
            });
        long endTime = System.currentTimeMillis();
        log.debug("[ParallelStream] 서버 구동시 종료 => 총 시간 : {}", endTime-startTime);
    }

    private void sequentialPrac(){

        log.debug("[Sequential] 서버 구동시 실행 size => {}", setNum().size());
        long startTime = System.currentTimeMillis();
        setNum().forEach(num -> {
//            log.debug(num + "번 실행!!");
            if(num % 1000 == 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        long endTime = System.currentTimeMillis();
        log.debug("[Sequential] 서버 구동시 종료 => 총 시간 : {}", endTime-startTime);
    }

    private List<Integer> setNum(){
        List<Integer> nums = new ArrayList<>();
        for (int startNum = 1; startNum <= 1000000; startNum++) {
            nums.add(startNum);
        }
        return nums;
    }


}
