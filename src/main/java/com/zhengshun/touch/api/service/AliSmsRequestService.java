package com.zhengshun.touch.api.service;



public interface AliSmsRequestService {


    Boolean sendShortMessage( String phone, String content, String code, String messageTempletCode, String type);

    Boolean sendShortMessage( String mobile, String content, String messageTempletCode, String type , Long tripId);
}