package com.capgemini.man.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private String userAccount;
	
    private String userName;
    
    private String userPassword;
    
    private String userDetail;
	
}
