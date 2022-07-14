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
    //上一次查看令牌数量的时间
    static long lastTime = System.currentTimeMillis();

    /**
     * @Description tokenBucket 令牌桶算法
     * @Author xhong103
     * @Date 2022-07-08 周五 上午 11:00
     * @param capacity  容量
     * @param rate 放入令牌的速度
     * @return boolean true 限流;false 不限流
     */
    static boolean tokenBucket(int capacity, int rate){
        //调用算法的时间
        long millisTime = System.currentTimeMillis();
        //两次请求的时间差
        long time = millisTime - lastTime;
        //可用令牌数
        cur = Math.min(capacity, cur + time * rate);
        //重置上次调用时间
        lastTime = millisTime;
        //如果令牌数为0,返回true
        if (cur == 0) {
            return true;
        }
        //拿走一个令牌
        --cur;
        //否则为false
        return false;
    }

    public static void main(String[] args) {
        for (; ;) {
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
