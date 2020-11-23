package edu.lab.mq;

import edu.lab.config.ApplicationConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private ConsumerManagement manager;

    @RabbitListener(queues = "detected")
    public void consume(String msg){
        System.out.println(msg);
        //new Thread(()->manager.setResponse(msg)).start();
        manager.setResponse(msg);
    }
}
