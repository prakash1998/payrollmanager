package com.pra.payrollmanager.security.authorization.servcie;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.exception.util.EntityType;
import com.pra.payrollmanager.security.authorization.dao.SecurityPermission;
import com.pra.payrollmanager.security.authorization.dto.SecurityPermissionDTO;
import com.pra.payrollmanager.security.authorization.repo.SecurityPermissionRepo;
import com.pra.payrollmanager.service.base.BaseServiceDTO;

@Service
public class SecurityPermissionService {

	@Autowired
	SecurityPermissionRepo repo;
	
	public List<SecurityPermissionDTO> getAll(){
		return repo.findAll().stream()
				.map(SecurityPermission::toDTO)
				.collect(Collectors.toList());
	}
	
	public SecurityPermissionDTO findById(String id) throws DataNotFoundEx {
		Optional<SecurityPermission> dbPermission = repo.findById(id);
		if(dbPermission.isPresent())
			return dbPermission.get().toDTO();
		else
			throw CheckedException.notFoundEx(EntityType.PERMISSION, String.valueOf(id));
	}
	
	public void updatePermission(SecurityPermissionDTO permission) throws DataNotFoundEx {
		String id = permission.toDAO().primaryKeyValue();
		Optional<SecurityPermission> dbPermission = repo.findById(id);
		if(dbPermission.isPresent())
			repo.save(permission.toDAO());
		else
			throw CheckedException.notFoundEx(EntityType.PERMISSION, id);
	}
	
}
