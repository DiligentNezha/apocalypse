package com.apocalypse.example.mq;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQProducer;
import com.aliyun.mq.http.model.TopicMessage;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/4/7
 */
public class ProducerTest {
    public static void main(String[] args) {
        MQClient mqClient = acquireMQClient();

        // 所属的 Topic
        final String topic = "common-topic";
        // Topic所属实例ID，默认实例为空
        final String instanceId = "MQ_INST_1263164536005935_BcPMHs0M";

        // 获取Topic的生产者
        MQProducer producer;
        if (instanceId != null && instanceId != "") {
            producer = mqClient.getProducer(instanceId, topic);
        } else {
            producer = mqClient.getProducer(topic);
        }

        try {
            // 循环发送4条消息
            TopicMessage pubMsg;
            pubMsg = new TopicMessage(
                    // 消息内容
                    "hello mq!".getBytes(),
                    // 消息标签
                    "good"
            );
            // 设置属性
            pubMsg.getProperties().put("name", "张三");
            // 定时消息, 定时时间为10s后

            pubMsg.setStartDeliverTime(System.currentTimeMillis() + 10 * 1000);
            // 同步发送消息，只要不抛异常就是成功
            TopicMessage pubResultMsg = producer.publishMessage(pubMsg);

            // 同步发送消息，只要不抛异常就是成功
            System.out.println(new Date() + " Send mq message success. Topic is:" + topic + ", msgId is: " + pubResultMsg.getMessageId()
                    + ", bodyMD5 is: " + pubResultMsg.getMessageBodyMD5());
        } catch (Throwable e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            System.out.println(new Date() + " Send mq message failed. Topic is:" + topic);
            e.printStackTrace();
        }

        mqClient.close();
    }

    public static MQClient acquireMQClient() {
        return new MQClient(
                // 设置HTTP接入域名（此处以公共云生产环境为例）
                "http://1263164536005935.mqrest.cn-qingdao-public.aliyuncs.com",
                // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
                "LTAI4FrPjUTki4ZcycfKzFcf",
                // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
                "9L3b07ojqSWNl888VEYOZxvgfhC92D"
        );
    }

    public static TopicMessage buildCommonMessage() throws UnsupportedEncodingException {
        TopicMessage topicMessage = new TopicMessage();
        topicMessage.setMessageBody("this is common message");
        topicMessage.setMessageTag("good");
        topicMessage.getProperties().put("Some Property", "Value");

        return topicMessage;
    }

}
