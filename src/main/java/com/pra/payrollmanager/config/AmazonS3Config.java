package com.pra.payrollmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.Getter;

@Configuration
public class AmazonS3Config {

    @Value("${amazon.aws.access-key-id}")
    private String accessKey;

    @Value("${amazon.aws.access-key-secret}")
    private String secretKey;

    @Value("${amazon.aws.s3.region}")
    private String region;
    
    @Getter
    @Value("${amazon.aws.s3.url}")
    private String s3BucketBaseUrl;

    @Bean
    public AmazonS3 amazonS3Client() {

        AWSCredentials awsCredentials =
                new BasicAWSCredentials(this.accessKey, this.secretKey);

        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}

