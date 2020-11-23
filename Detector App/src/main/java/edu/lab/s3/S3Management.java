package edu.lab.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import edu.lab.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;

@Component
public class S3Management implements PhotoStorage{
    private final ScheduledExecutorService pool = Executors.newScheduledThreadPool(6);
    private String bucket = "photos-for-detect";

    private AmazonS3 s3;

    public S3Management() {
        s3 = S3Builder.build(ApplicationConfig.getProperty(ApplicationConfig.S3_ACCESS_KEY),
                ApplicationConfig.getProperty(ApplicationConfig.S3_SECRET_KEY),
                ApplicationConfig.getProperty(ApplicationConfig.S3_SERVICE_ENDPOINT),
                ApplicationConfig.getProperty(ApplicationConfig.S3_REGION));
    }

    @Override
    public String save(MultipartFile file){
        System.out.println(file);
        String key = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        try {
            s3.putObject(bucket, key, file.getInputStream(), new ObjectMetadata());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        pool.schedule(() -> delete(key), 3L, TimeUnit.HOURS);
        return key;

    }

    @Override
    public String getFileUrl(String bucket, String key){
        return s3.getUrl(bucket, key).toString();
    }

    @Override
    public void delete(String key){
        s3.deleteObject(bucket, key);
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBucket() {
        return bucket;
    }
}
