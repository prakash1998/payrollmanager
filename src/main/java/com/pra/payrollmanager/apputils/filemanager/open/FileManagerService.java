package com.pra.payrollmanager.apputils.filemanager.open;

import java.net.URL;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.pra.payrollmanager.apputils.filemanager.AppFile;
import com.pra.payrollmanager.apputils.filemanager.PublicFileService;
import com.pra.payrollmanager.base.services.ServiceDAO;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;

@Service
public class FileManagerService
		extends ServiceDAO<ObjectId, AppFile, CommonAppFileDAL> {
	
	@Autowired
	private PublicFileService fileService;
	
	public URL getBaseURL() {
		return fileService.baseUrlToAccessFiles();
	}
	
	@Override
	public AppFile create(AppFile obj) throws DuplicateDataEx {
		AppFile savedFile = super.create(obj.setSecured(false));
		return savedFile.setUrl(fileService.getSignedUrl(savedFile.getId().toHexString(), HttpMethod.PUT));
	}

}
