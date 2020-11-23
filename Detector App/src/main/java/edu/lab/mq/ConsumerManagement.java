package edu.lab.mq;

import org.springframework.stereotype.Component;

@Component
public class ConsumerManagement {
    private static volatile boolean monitor = false;
    private String response;

    public String getConsumerResponse(){
        while(!monitor){

        }
        monitor = false;
        return response;
    }

    public void setResponse(String response){
        this.response = response;
        monitor = true;
    }
}
