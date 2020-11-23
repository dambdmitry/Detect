package edu.lab.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    public static final String S3_ACCESS_KEY = "s3.access-key";
    public static final String S3_SECRET_KEY = "s3.secret-key";
    public static final String S3_REGION = "s3.region";
    public static final String S3_SERVICE_ENDPOINT = "s3.service-endpoint";
    public static final String S3_BUCKET_TO_SAVE = "s3.bucket.to-save.name";
    public static final String S3_BUCKET_TO_LOAD = "s3.bucket.to-load.name";
    public static final String RABBIT_EXCHANGE = "rabbit.exchange";
    public static final String RABBIT_CONSUMER_ROUTING_KEY = "rabbit.routing-key-for-consumer";
    public static final String RABBIT_PRODUCER_ROUTING_KEY = "rabbit.routing-key-for-producer";
    public static final String RABBIT_QUEUE_FOR_CONSUME = "rabbit.queue-for-consume";
    public static final String RABBIT_QUEUE_FOR_PRODUCE = "rabbit.queue-for-produce";

    public static Properties properties = new Properties();

    private static final String applicationPropertiesPath = "application.properties";

    public static String getProperty(String nameProperty){
        if(properties.isEmpty()){
            try(InputStream inputStream = ApplicationConfig.class.getClassLoader().getResourceAsStream(applicationPropertiesPath)){
                properties.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return properties.getProperty(nameProperty);
    }

}

