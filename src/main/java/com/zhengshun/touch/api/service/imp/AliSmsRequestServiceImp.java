package com.zhengshun.touch.api.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.zhengshun.touch.api.common.context.Global;
import com.zhengshun.touch.api.common.util.HttpsUtil;
import com.zhengshun.touch.api.common.util.code.MD5;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.mapper.TbUserMapper;
import com.zhengshun.touch.api.service.AliSmsRequestService;
import com.zhengshun.touch.api.service.WXRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AliSmsRequestServiceImp implements AliSmsRequestService {


    public static final Logger logger = LoggerFactory.getLogger(AliSmsRequestServiceImp.class);

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public Map<String, String> sendCode(String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone );
        params.put("SignName", Global.getValue("ali_sms_sign_name"));
        params.put("TemplateCode", "SMS_142953508");
        return null;
    }


}