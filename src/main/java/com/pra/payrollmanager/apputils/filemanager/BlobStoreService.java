package com.pra.payrollmanager.apputils.filemanager;

import java.util.function.Function;
import java.util.function.Supplier;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobRequestSigner;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class BlobStoreService {

	@Value("${blobstore.id}")
	private String identity;

	@Value("${blobstore.secret}")
	private String credentials;

	@Getter
	@Value("${blobstore.provider}")
	private String provider;

	public Supplier<BlobStoreContext> contextGetter() {
		return () -> ContextBuilder.newBuilder(provider)
				.credentials(identity, credentials)
				.buildView(BlobStoreContext.class);
	}

	public HttpRequest getSignedRequest(Function<BlobRequestSigner, HttpRequest> reqSigner) {
		BlobStoreContext blobStoreContext = contextGetter().get();
		HttpRequest signedRequest = reqSigner.apply(blobStoreContext.getSigner());
		blobStoreContext.close();
		return signedRequest;
	}

	public String performBlobStoreOp(Function<BlobStore, String> reqSigner) {
		BlobStoreContext blobStoreContext = contextGetter().get();
		String etag = reqSigner.apply(blobStoreContext.getBlobStore());
		blobStoreContext.close();
		return etag;
	}

}
