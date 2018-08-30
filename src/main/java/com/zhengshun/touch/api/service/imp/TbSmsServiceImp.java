package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.common.context.Global;
import com.zhengshun.touch.api.common.util.DateUtils;
import com.zhengshun.touch.api.common.util.StringUtil;
import com.zhengshun.touch.api.domain.TbSms;
import com.zhengshun.touch.api.domain.TbSmsTpl;
import com.zhengshun.touch.api.mapper.TbSmsMapper;
import com.zhengshun.touch.api.mapper.TbSmsTplMapper;
import com.zhengshun.touch.api.service.SmsMessageService;
import com.zhengshun.touch.api.service.TbSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TbSmsServiceImp implements TbSmsService {


    public static final Logger logger = LoggerFactory.getLogger(TbSmsServiceImp.class);


    @Autowired
    private TbSmsTplMapper tbSmsTplMapper;

    @Autowired
    private TbSmsMapper tbSmsMapper;

    @Autowired
    private SmsMessageService smsMessageService;

    @Override
    public Boolean sendCode(String phone, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "code");
        TbSmsTpl tbSmsTpl = tbSmsTplMapper.findSelective( params );
        if ( tbSmsTpl != null) {
            int vcode = 0;
            String messageChannl = Global.getValue("sms_passageway");
            String registerCode = getCode(phone, type);
            if (StringUtil.isNotBlank(registerCode)){
                vcode = Integer.parseInt(registerCode);
            }else {
                vcode = (int) (Math.random() * 9000) + 1000;
            }
            return smsMessageService.sendShortMessage(phone, vcode + "", tbSmsTpl.getNumber(), type, messageChannl);

        }

        logger.info("【AliSmsRequestServiceImp】【sendCode】 未找到短信模板 type = code ");
        return false;
    }

    @Override
    public String getCode(String phone, String type) {
        Map<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("smsType", type);
        TbSms tbSms = tbSmsMapper.findTimeMsg(data);
        if (tbSms != null) {
            Date sendTime = tbSms.getSendTime();
            Date date = DateUtils.addMinute(sendTime, 15);
            Date nowDate = new Date(System.currentTimeMillis());
            if (date.after(nowDate)) {
                return tbSms.getCode();
            }
        }
        return null;
    }
}