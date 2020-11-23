package edu.lab.mq;

import edu.lab.config.ApplicationConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private RabbitTemplate template;

    private String exchange = ApplicationConfig.getProperty(ApplicationConfig.RABBIT_EXCHANGE);
    private String routingKey = ApplicationConfig.getProperty(ApplicationConfig.RABBIT_PRODUCER_ROUTING_KEY);
    private String queue = ApplicationConfig.getProperty(ApplicationConfig.RABBIT_QUEUE_FOR_PRODUCE);

    @Bean
    public Queue queue(){
        return new Queue(queue, false);
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    public void produce(String url){
        template.convertAndSend(exchange, routingKey, url);
    }

}
