package com.jackssy.admin.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class RabbitMQ {

    private static String QUEUE_NAME = "test-weibo";

    /**
     * 获取连接
     * @return Connection
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.93.233.127");
        factory.setPort(5672);
        //设置vhost
        factory.setVirtualHost("/");
        factory.setUsername("weibo");
        factory.setPassword("weibo1234");
        //通过工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }

    //创建队列，发送消息
    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明创建队列
        channel.queueDeclare(QUEUE_NAME, false,false,false,null);
        //消息内容
        String message = "Hello World!！！";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("发送消息：" + message);
        //关闭连接和通道
        channel.close();
        connection.close();
    }

}