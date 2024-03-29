package net.man.controller;

import net.man.entity.UserInfo;
import net.man.dto.JWTInfoDTO;
import net.man.controller.response.BaseResponse;
import net.man.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/login")

public class LoginController {

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	net.man.service.JWTRedisService JWTRedisService;


	/**
	 * @param userAccount
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/token", method = {RequestMethod.POST, RequestMethod.GET})
	public BaseResponse token(
			@RequestParam String userAccount,
			@RequestParam String password,
			HttpServletRequest request, HttpServletResponse response) {
		BaseResponse baseResponse = new BaseResponse();
		UserInfo userInfo = userInfoService.selectByUserAccount(userAccount);
		if (StringUtils.isEmpty(userAccount)) {
			return BaseResponse.newInstanceFailed("账户为空");
		} else if (StringUtils.isEmpty(password)) {
			return BaseResponse.newInstanceFailed("密码为空");
		} else if (null == userInfo) {
			return BaseResponse.newInstanceFailed("用户不存在");
		}
		JWTInfoDTO jwtInfoDTOEntity = JWTRedisService.create(userInfo.getUserId());
		return BaseResponse.newInstanceSuccess(jwtInfoDTOEntity);
	}
}
