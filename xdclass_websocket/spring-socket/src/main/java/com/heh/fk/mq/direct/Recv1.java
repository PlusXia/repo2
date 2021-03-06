package com.heh.fk.mq.direct;

import com.rabbitmq.client.*;

import java.io.IOException;

@SuppressWarnings("all")
public class Recv1 {
    private final static String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("121.199.77.205");
        factory.setUsername("admin");
        factory.setPassword("password");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        //绑定交换机，fanout扇形，即广播
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        //获取队列
        String queueName = channel.queueDeclare().getQueue();

        //绑定交换机和队列, direct交换机需要指定routingkey
        channel.queueBind(queueName, EXCHANGE_NAME, "errorRoutingKey");
        channel.queueBind(queueName, EXCHANGE_NAME, "infoRoutingKey");
        channel.queueBind(queueName, EXCHANGE_NAME, "debugRoutingKey");


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("body=" + new String(body, "utf-8"));

                //手工确认消息消费，不是多条确认
                channel.basicAck(envelope.getDeliveryTag(), false);

            }
        };

        //消费,关闭消息消息自动确认,重要
        channel.basicConsume(queueName, false, consumer);
    }
}
