package com.capgemini.man.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTInfoEntity {
	 private Long userId;

	 private String token;

}
