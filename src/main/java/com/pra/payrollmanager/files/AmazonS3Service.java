//package com.pra.payrollmanager.files;
//
//import java.io.File;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//
//@Service
//public class AmazonS3Service {
//
//	@Value("${amazon.aws.s3.bucket1}")
//	private String bucketName;
//
//	@Autowired
//	private AmazonS3 s3Client;
//
//	public String deleteFileFromBucket(String key) {
//		s3Client.deleteObject(new DeleteObjectRequest(this.bucketName, key));
//		return "File deleted successfully";
//	}
//	
//    public void uploadFileTos3bucket(String fileName, File file) {
//        s3Client.putObject(new PutObjectRequest(this.bucketName, fileName, file)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//    }
//
//}
