package com.pra.payrollmanager.apputils.filemanager;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.pra.payrollmanager.config.AmazonS3Config;
import com.pra.payrollmanager.exception.unchecked.AppException;

@Service
public class PublicFileService implements BucketFileService {

	@Value("${amazon.aws.s3.bucket.public}")
	private String publicBucket;

	@Override
	public String bucket() {
		return publicBucket;
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

	public URL baseUrlToAccessFiles() {
		try {
			return new URL(s3BaseURLforBucket());
		} catch (MalformedURLException e) {
			throw new AppException("Invalid S3 public bucket url");
		}
	}

	public URL urlToGetPublicFile(String fileId) {
		try {
			return new URL(String.format("%s/%s",s3BaseURLforBucket(), fileId));
		} catch (MalformedURLException e) {
			throw new AppException("Invalid S3 public bucket url");
		}
	}
}
