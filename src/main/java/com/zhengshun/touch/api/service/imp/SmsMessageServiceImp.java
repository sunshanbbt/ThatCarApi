package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.service.AliSmsRequestService;
import com.zhengshun.touch.api.service.SmsMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsMessageServiceImp implements SmsMessageService {


    public static final Logger logger = LoggerFactory.getLogger(SmsMessageServiceImp.class);

    @Autowired
    private AliSmsRequestService aliSmsRequestService;

    @Override
    public Boolean sendShortMessage(String phone, String code, String messageTemplet, String type, String channel) {

        if ( channel.equals("10") ) { // 阿里云短信
            return aliSmsRequestService.sendShortMessage(phone, code, messageTemplet, type );
        }
        return false;
    }

    @Override
    public Boolean sendShortMessage(String phone, String content, String type, String channel) {
        return null;
    }
}