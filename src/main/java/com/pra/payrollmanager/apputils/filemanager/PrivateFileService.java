package com.pra.payrollmanager.apputils.filemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.pra.payrollmanager.config.AmazonS3Config;

@Service
public class PrivateFileService implements BucketFileService{

	@Value("${amazon.aws.s3.bucket.private}")
	private String secureBucket;

	@Override
	public String bucket() {
		return this.secureBucket;
	}

	@Autowired
	private AmazonS3 s3Client;
	
	@Override
	public AmazonS3 s3Client() {
		return s3Client;
	}
	
	@Autowired
	private AmazonS3Config s3Config;

	@Override
	public AmazonS3Config s3Config() {
		return s3Config;
	}
	
}
