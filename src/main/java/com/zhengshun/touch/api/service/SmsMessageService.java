package com.zhengshun.touch.api.service;



public interface SmsMessageService {


    Boolean sendShortMessage(String phone, String content, String code, String messageTemplet, String type, String
            channel);

    Boolean sendShortMessage(String phone, String content, String messageTemplet, String type, String channel, Long tripId);
}