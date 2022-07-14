package com.eetig.study0714;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @title: TransactionMQProducerExample
 * @author: xhong103
 * @date: 2022/7/14 16:04
 */

public class TransactionMQProducerExample {

    public static void main(String[] args) throws MQClientException,InterruptedException, UnsupportedEncodingException {
        //自定义消息生产者
        TransactionMQProducer mqProducer = new TransactionMQProducer("test_transaction_producer");
        //自定义执行器
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 5, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    }
                });
        //生产者属性设置: 执行器
        mqProducer.setExecutorService(executorService);
        //事务监视器
        TransactionListener transactionListener = new TransactionListenerImpl();
        //生产者属性设置: 事务监视器
        mqProducer.setTransactionListener(transactionListener);
        //mq地址
        mqProducer.setNamesrvAddr("127.0.0.1:9876");
        //启动生产者线程
        mqProducer.start();
        //订单
        Order order = new Order("66666", "books");
        //消息
        Message msg = new Message("transaction_tp", JSON.toJSONString(order).getBytes(RemotingHelper.DEFAULT_CHARSET));
        //发送消息
        TransactionSendResult sendResult = mqProducer.sendMessageInTransaction(msg, null);
        //获得消息的消费信息
        System.out.println("sendResult.getSendStatus() = " + sendResult.getSendStatus());
        //关闭生产者
        mqProducer.shutdown();
    }

}
