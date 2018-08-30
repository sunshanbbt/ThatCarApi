package com.zhengshun.touch.api.service;



public interface SmsMessageService {


    Boolean sendShortMessage(String phone, String code, String messageTemplet, String type, String channel);

    Boolean sendShortMessage(String phone, String content, String type, String channel);
}