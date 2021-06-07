package com.zheng.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ZhengTianLiang
 * @date: 2021/06/06  17:46
 * @desc: rabbitmq的一些设置类
 */

@Configuration
public class RabbitMQConfig {

    // 定义队列名称

    /**
     * 正常队列名称，将同步mysql数据库中的数据
     */
    public String queueName1 = "skill.queue.order";

    /**
     * 延迟队列(没有消费者的队列)，是为了让队列中的消息过期，并进入死信队列用的队列
     */
    public String queueName2 = "skill.queue.ttlorder";

    /**
     * 死信队列名称，消息进入死信交换机后，根据路由键发送到死信队列中，消费者监听死信队列，这个就是死信队列
     */
    public String queueName3 = "skill.queue.dlxorder";

    // 定义交换机名称

    /**
     * fanout类型的交换机，直接转发这个交换机的全部队列，将订单消息发送到这个交换机的全部队列中
     */
    public String exchange1 = "skill.exchange.order";

    /**
     * 死信交换机，将进入到死信交换器的消息，根据路由规则，发送到不同的队列中去，
     * 此次用direct类型，有点对点(direct)、发布订阅(fanout)、主题(topic)、头部交换器(head)四种
     */
    public String exchange2 = "skill.exchange.dlxorder";

    // 定义路由键名称
    /**
     * 是消息进入到死信交换机，死信交换机根据路由键将死信消息发送到不同的队列中的。这个是死信交换机
     */
    public String routingKey1 = "skill.orderdlx";

    // 定义超时时间
    /**
     * 定义队列的过期时间，单位是秒。是说这个队列内的全部的消息的过期时间
     */
    public Integer msgttl = 15 * 60;   // 现在没乘1000，现在乘的话，不太好看，用的时候乘。


    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  19:10
     * @desc: 创建一个普通队列
     */
    @Bean
    public Queue createQueue1(){
        return new Queue(queueName1);
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  19:11
     * @desc: 创建一个延迟队列
     */
    @Bean
    public Queue createQueue2(){
        // 定义一些队列的参数
        Map<String,Object> args = new HashMap<>();
        // 队列的过期时间，注意，这几个map前面的key都是固定的
        args.put("x-message-ttl",msgttl*1000);
        // 设置死信队列的交换器
        args.put("x-dead-letter-exchange",exchange2);
        // 设置死信队列的路由键
        args.put("x-dead-routing-key",routingKey1);

        // 将参数设置进队列中
        return QueueBuilder.durable(queueName2) // 队列名
                .withArguments(args) // 参数
                .build(); // build
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  19:17
     * @desc: 创建一个死信队列
     */
    @Bean
    public Queue createQueue3(){
        return new Queue(queueName3);
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  19:24
     * @desc: 创建一个fanout交换机，fanout是将消息往它的全部的队列中挨着发送一遍
     */
    @Bean
    public FanoutExchange createFanoutExchange(){
        return new FanoutExchange(exchange1);
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  19:27
     * @desc: 创建一个direct交换机，direct是点对点消息，根据路由键，精准匹配，匹配成功后，发送
     *  (交换机根据路由键，匹配上了队列，才会发送，)
     */
    @Bean
    public DirectExchange createDirectExchange(){
        return new DirectExchange(exchange2);
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  19:30
     * @desc: 将队列和路由键绑定，你光创建出来了，队列和交换器没有任何关系，需要绑定，才能联系起来
     */
    @Bean
    public Binding createB1(){
        return BindingBuilder.bind(createQueue1()) // 队列名
                .to(createFanoutExchange());    // 交换器名，因为是fanout类型，所以不用写路由键
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  19:30
     * @desc: 将队列和路由键绑定，这是第二个队列，业务需求要fanout类型交换机需要绑定两个队列，
     *      一个普通的正常队列，一个延时队列，需要往这俩中同事发消息
     */
    @Bean
    public Binding createB2(){
        return BindingBuilder.bind(createQueue2()) // 队列名
                .to(createFanoutExchange());    // 交换器名，因为是fanout类型，所以不用写路由键
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  19:34
     * @desc: 将队列和路由键绑定，因为是direct点对点的交换器，所以需要加上路由键
     */
    @Bean
    public Binding createB3(){
        return BindingBuilder.bind(createQueue3())
                .to(createDirectExchange())
                .with(routingKey1);
    }

}
