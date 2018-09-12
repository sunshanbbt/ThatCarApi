package com.zhengshun.touch.api.service;

import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.TbUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface TbUserService extends BaseService<TbUser, Long> {

    Boolean saveUser(HttpServletRequest request, String avatarUrl, String city, String country, Integer gender, String
            language, String nickName, Long userId, String province );

    Map<String, Object> updateUnlockPwd(TbUser tbUser, String unlockPwd);

    Map<String, Object> updateRiskPwd(TbUser tbUser, String riskPwd);

    Map<String, Object> verifyPwd(TbUser tbUser,String unlockPwd );

    Map<String, Object> updateInfo(String wxNo,String realName, String phone, Long userId);

    TbUser getUserByRdSessionKey(String rdSessionKey);

    TbUser getUserById( Long id );

    Map<String, Object> sendSms( TbUser tbUser);
}