package com.pra.payrollmanager.apputils.filemanager;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

interface BaseFileService {
	
	long DEFAULT_EXPIRATION_TIME = Duration.ofMinutes(10).getSeconds();
	
	default long urlExpirationTime(){
		return DEFAULT_EXPIRATION_TIME;
	}
	
	AmazonS3 s3Client();

	default String deleteFileFromBucket(String bucket,String fileIdToDelete) {
		CompletableFuture.runAsync(() -> {			
			s3Client().deleteObject(new DeleteObjectRequest(bucket, fileIdToDelete));
		});
		return fileIdToDelete;
	}
	
	default URL getSignedUrlToUpdateFile(String bucket,String fileIdToDelete) {
		this.deleteFileFromBucket(bucket, fileIdToDelete);
		return this.getSignedUrlToSaveFile(bucket);
	}
	
	default URL getSignedUrlToGetFile(String bucket,String fileId) {
		return this.getSignedUrl(bucket, fileId , HttpMethod.GET);
	}
	
	default URL getSignedUrlToSaveFile(String bucket) {
		String fileId = UUID.randomUUID().toString();
		return this.getSignedUrl(bucket, fileId , HttpMethod.PUT);
	}
	
	default URL getSignedUrl(String bucket, String fileId,HttpMethod httpMethod) {
		// Generate the pre-signed URL.
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileId)
				.withMethod(httpMethod)
				.withExpiration(Date.from(Instant.now().plusSeconds(urlExpirationTime())));

		return s3Client().generatePresignedUrl(generatePresignedUrlRequest);
	}
	
}
