package net.man.entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleInfo implements GrantedAuthority{
	private static final long serialVersionUID = 7088351652237386029L;

	private Long roleId;
	
	private String roleName;
	
	private String roleDesc;

	@Override
	public String getAuthority() {
		return roleName;
	}
}
