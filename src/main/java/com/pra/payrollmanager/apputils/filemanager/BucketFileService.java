package com.pra.payrollmanager.apputils.filemanager;

import java.net.URL;
import java.text.MessageFormat;

import com.amazonaws.HttpMethod;
import com.pra.payrollmanager.config.AmazonS3Config;

public interface BucketFileService extends BaseFileService {

	String bucket();

	AmazonS3Config s3Config();

	default String s3BaseURLforBucket() {
		return MessageFormat.format(s3Config().getS3BucketBaseUrl(), bucket());
	}

	default String deleteFileFromBucket(String fileIdToDelete) {
		return this.deleteFileFromBucket(bucket(), fileIdToDelete);
	}

	default URL getSignedUrlToUpdateFile(String fileIdToDelete) {
		String bucket = bucket();
		this.deleteFileFromBucket(bucket, fileIdToDelete);
		return this.getSignedUrlToSaveFile(bucket);
	}

	default URL getSignedUrlToGetFile(String fileId) {
		return this.getSignedUrlToGetFile(bucket(), fileId);
	}

	default URL getSignedUrlToSaveFile() {
		return this.getSignedUrlToSaveFile(bucket());
	}

	default URL getSignedUrl(String fileId, HttpMethod httpMethod) {
		return this.getSignedUrl(bucket(),fileId, httpMethod);
	}
}
