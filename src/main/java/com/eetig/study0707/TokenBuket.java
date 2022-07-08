package com.eetig.study0707;

import java.util.Random;

/**
 * @title: TokenBuket
 * @author: xhong103
 * @date: 2022/7/7 16:54
 */

public class TokenBuket {

    //当前令牌数
    static long cur = 0;

    static long lastTime = System.currentTimeMillis();

    static boolean tokenBucket(int capacity, int rate){

        long millisTime = System.currentTimeMillis();

        long time = millisTime - lastTime;

        cur = Math.min(capacity, cur + time * rate);

        lastTime = millisTime;

        if (cur == 0) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        for (int i = 0; ; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            int randomTime = new Random().nextInt(1500);
            for (int j = 0; j < randomTime; j++) {
                request();
            }

        }
    }

    private synchronized static void request() {
        if (tokenBucket(2000, 1)) {
            System.out.println("限流了。。。cur = " + cur);
        } else {
            System.out.println("没限流。。。cur = " + cur);
        }

    }


}
