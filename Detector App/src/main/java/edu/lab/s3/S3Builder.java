package edu.lab.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.stereotype.Component;

@Component
public final class S3Builder {

    private static AmazonS3 s3;

    public static AmazonS3 build(String accessKey, String secretKey, String serviceEndpoint, String region){
        if(s3 == null){
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

            s3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(
                            new AmazonS3ClientBuilder.EndpointConfiguration(serviceEndpoint,region)
                    ).build();
        }
        return s3;
    }
}
