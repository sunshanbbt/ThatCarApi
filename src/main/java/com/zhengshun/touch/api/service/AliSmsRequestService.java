package com.zhengshun.touch.api.service;



public interface AliSmsRequestService {


    Boolean sendShortMessage( String phone, String code, String messageTempletCode, String type );

    Boolean sendShortMessage( String mobile, String content, String type );
}