package com.heh.fk.mq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("121.199.77.205");
        factory.setUsername("admin");
        factory.setPassword("password");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //一般是固定的，可以作为会话的名称
                System.out.println("consumerTag="+consumerTag);
                //可以获取交换机、路由健等信息
                System.out.println("envelope="+envelope);

                System.out.println("properties="+properties);

                System.out.println("body="+new String(body,"utf-8"));

            }
        };

        //消费
        channel.basicConsume(QUEUE_NAME,true,consumer);

    }
}
