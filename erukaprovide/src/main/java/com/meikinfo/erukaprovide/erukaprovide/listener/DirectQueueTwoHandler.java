package com.meikinfo.erukaprovide.erukaprovide.listener;

import cn.hutool.json.JSONUtil;
import com.meikinfo.erukaprovide.erukaprovide.consts.MessageStruct;
import com.meikinfo.erukaprovide.erukaprovide.consts.RabbitConsts;
import com.meikinfo.erukaprovide.erukaprovide.service.EsDataService;
import com.meikinfo.erukaprovide.erukaprovide.service.inteface.MailService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 直接队列1 处理器
 * </p>
 *
 * @description: 队列2 处理器
 */
@Slf4j
@RabbitListener(queues = RabbitConsts.QUEUE_TWO)
@Component
public class DirectQueueTwoHandler {

    @Autowired
    private EsDataService esDataService;

    /**
     * 如果 spring.rabbitmq.listener.direct.acknowledge-mode: auto，则可以用这个方式，会自动ack
     */
    // @RabbitHandler
    public void directHandlerAutoAck(MessageStruct message) {
        log.info("直接队列处理器，接收消息：{}", JSONUtil.toJsonStr(message));
    }

    @RabbitHandler
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.debug("直接队列1，手动ACK，接收消息：{}", JSONUtil.parseObj(messageStruct));

            //导入数据完成,构建es人员信息数据库
            esDataService.buildPersonDb();
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
