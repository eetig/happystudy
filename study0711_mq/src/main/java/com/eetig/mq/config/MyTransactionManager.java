package com.eetig.mq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @title: MyTransactionManager
 * @author: xhong103
 * @date: 2022/7/11 11:29
 */
@Component
public class MyTransactionManager extends DataSourceTransactionManager{

    @Autowired
    MQProducer mqProducer;

    public MyTransactionManager(DataSource dataSource){
        super(dataSource);
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {
        try {
            //发送threadlocal内所有带发送的mq
            super.doCommit(status);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            sendMQ();
        }
    }

    private void sendMQ() {
        try {
            //发送threadlocal内所有待发送的mq
            mqProducer.sendAllThreadLoalMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
