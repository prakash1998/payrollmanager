package com.pra.payrollmanager.apputils.filemanager.open;

import java.net.URL;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.apputils.filemanager.AppFile;
import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.response.dto.Response;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("utils/file-manager/public")
public class FileManagerControl extends BaseControl<FileManagerService> {

	@GetMapping(value = "base-url", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<URL> getBaseUrl() {
		return Response.payload(service.getBaseURL());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<AppFile> createFile(@Valid @RequestBody AppFile file) throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.create(file));
	}

}
