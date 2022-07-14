package com.eetig.mq.service;

import com.alibaba.fastjson.JSONObject;
import com.eetig.mq.mapper.MsgRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.util.List;

/**
 * @title: MQProducer
 * @author: xhong103
 * @date: 2022/7/11 13:05
 */
@Component
@Slf4j
public class MQProducer {

    private ThreadLocal<List<Long>> threadLocal = new ThreadLocal<>();

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Autowired
    MsgRecordMapper msgRecordMapper;

    public SendResult send(String topic, Object value){
        try {
            return defaultMQProducer.send(new Message(topic, JSONObject.toJSONString(value).getBytes()));
        } catch (Exception e){
            log.info("SendResult:  "+e);
        }
        return null;
    }
}
