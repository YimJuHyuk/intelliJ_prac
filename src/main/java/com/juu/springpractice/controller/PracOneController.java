package com.juu.springpractice.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@RestController("/prac")
@Slf4j
public class PracOneController {

//    static final Logger log = LoggerFactory.getLogger(PracOneController.class);
   @PostConstruct
   public void init() throws Exception{
       log.debug("##### 병렬, 순차처리 속도 테스트 #####");
       fokeJoinPoolPrac();
       sequentialPrac();
   }

   private void fokeJoinPoolPrac() throws Exception{
       int exponent = 2;

       Integer parallelism = (int) Math.pow(Double.valueOf(2), Double.valueOf(exponent));
       ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

       log.debug("[ParallelStream] 서버 구동시 실행");
       long startTime = System.currentTimeMillis();
       forkJoinPool.submit(() -> {
           setNum().parallelStream().forEach(num -> {
               log.debug(num + "번 실행!!");
          });
       }).get();
       long endTime = System.currentTimeMillis();
       log.debug("[ParallelStream] 서버 구동시 종료 => 총 시간 : {}", endTime-startTime);
   }

    private void sequentialPrac(){
        int exponent = 2;

        Integer parallelism = (int) Math.pow(Double.valueOf(2), Double.valueOf(exponent));
        ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);

        log.debug("[Sequential] 서버 구동시 실행");
        long startTime = System.currentTimeMillis();
        setNum().forEach(num -> {
            log.debug(num + "번 실행!!");
        });

        long endTime = System.currentTimeMillis();
        log.debug("[Sequential] 서버 구동시 종료 => 총 시간 : {}", endTime-startTime);
    }

    private List<Integer> setNum(){
        List<Integer> nums = new ArrayList<>();
        for (int startNum = 1; startNum <= 1000; startNum++) {
            nums.add(startNum);
        }
        return nums;
    }


}
