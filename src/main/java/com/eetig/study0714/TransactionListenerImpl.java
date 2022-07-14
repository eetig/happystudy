package com.eetig.study0714;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * @title: TransactionListenerImpl
 * @author: xhong103
 * @date: 2022/7/14 16:15
 */

public class TransactionListenerImpl implements TransactionListener {

    /**
     * @Description executeLocalTransaction 半消息发送成功将会自动执行该逻辑
     * @Author xhong103
     * @Date 2022-07-14 周四 下午 4:23
     * @param message
     * @param o
     * @return LocalTransactionState
     */
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        //执行本地事务
        Order order = null;
        try {
            order = JSON.parseObject(new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET), Order.class);
            boolean isSuccess = updateOrder(order);
            if (isSuccess) {
                System.out.println(" 本地事务执行成功 " + isSuccess);
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                System.out.println(" 本地事务执行失败 " + isSuccess);
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }

        } catch (Exception e){
            System.out.println(" 本地事务执行异常 " + e);
        }

        return LocalTransactionState.UNKNOW;
    }

    /**
     * @Description updateOrder 更新订单,模拟数据库更新,返回事务执行成功
     * @Author xhong103
     * @Date 2022-07-14 周四 下午 4:37
     * @param order
     * @return boolean
     */
    private boolean updateOrder(Order order) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return true;
    }

    /**
     * @Description checkLocalTransaction 若提交/回滚事务消息失败,rocketmq自动反查事务状态
     * @Author xhong103
     * @Date 2022-07-14 周四 下午 4:38
     * @param messageExt
     * @return LocalTransactionState
     */
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        try {
            Order order = JSON.parseObject(new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET), Order.class);
            boolean isSuccess = queryOrder(order.getOrderId());
            if (isSuccess) {
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }

        } catch (Exception e){
            System.out.println(" 查询失败 " + e);
        }
        return LocalTransactionState.UNKNOW;
    }

    /**
     * @Description queryOrder 查询订单状态
     * @Author xhong103
     * @Date 2022-07-14 周四 下午 4:50
     * @param orderId
     * @return boolean
     */
    private boolean queryOrder(String orderId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return true;
    }
}
