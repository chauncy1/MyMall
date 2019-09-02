package com.capgemini.man.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
	private Long userId;
	
	private String userAccount;
	
    private String userName;
    
    private String userPassword;
    
    private String userDetail;
	
}
