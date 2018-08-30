package com.zhengshun.touch.api.service;

import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.TbUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface TbUserService extends BaseService<TbUser, Long> {

    Boolean saveUser(HttpServletRequest request, String avatarUrl, String city, String country, Integer gender, String
            language, String nickName, String rdSessionKey, String province );

    Boolean updateUnlockPwd(HttpServletRequest request, String unlockPwd, String rdSessionKey);

    Boolean updateRiskPwd(HttpServletRequest request, String riskPwd, String rdSessionKey);

    Boolean verifyPwd(HttpServletRequest request, String unlockPwd, String rdSessionKey);

    Boolean updateInfo(String wxNo,String realName, String phone, String rdSessionKey);

    TbUser getUserByRdSessionKey(String rdSessionKey);
}