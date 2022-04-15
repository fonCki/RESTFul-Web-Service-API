package com.example.casework2.subscriber;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class KitchenSubscriber {
    private final static String QUEUE_NAME = "kitchen.orders";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override public void handleDelivery(String consumerTag,
                                                 Envelope envelope,
                                                 AMQP.BasicProperties properties,
                                                 byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Message: --> " + message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
