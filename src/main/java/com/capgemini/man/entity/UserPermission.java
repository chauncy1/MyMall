package com.capgemini.man.entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class UserPermission implements GrantedAuthority{
	private static final long serialVersionUID = -3798316330492714492L;
	
	private String authority;
	
	@Override
	public String getAuthority() {
		return this.authority;
	}

}
