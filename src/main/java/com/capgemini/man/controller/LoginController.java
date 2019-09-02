package com.capgemini.man.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.man.controller.response.BaseResponse;
import com.capgemini.man.entity.JWTInfoEntity;
import com.capgemini.man.entity.UserInfo;
import com.capgemini.man.service.JWTRedisService;
import com.capgemini.man.service.UserInfoService;

@RestController
@RequestMapping(value = "/login")

public class LoginController
{
    
    @Autowired
    UserInfoService userInfoService;
    
    @Autowired
    JWTRedisService JWTRedisService;
    

    /**
     * @param userAccount �û����
     * @param password �û�����
     * @param request �������
	 * @param response ���ض���
     * @return
     */
    @RequestMapping(value = "/token", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseResponse token(
            		@RequestParam String userAccount,
                    @RequestParam String password, 
                    HttpServletRequest request, HttpServletResponse response){
    	BaseResponse baseResponse= new BaseResponse();
    	UserInfo userInfo = userInfoService.selectByUserAccount(userAccount);
        if(userAccount == null){
        	return BaseResponse.newInstanceFailed("账户为空");
        }
        else if(password == null || password.trim() == ""){
        	return BaseResponse.newInstanceFailed("密码为空");
        }else if(null == userInfo) {
        	return BaseResponse.newInstanceFailed("用户不存在");
        }
        JWTInfoEntity jwtInfoEntity = JWTRedisService.create(userInfo.getUserId());
        return BaseResponse.newInstanceSuccess(jwtInfoEntity);
    }
}
