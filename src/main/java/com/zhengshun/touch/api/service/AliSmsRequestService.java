package com.zhengshun.touch.api.service;


import java.util.Map;

public interface AliSmsRequestService {


    Map<String, String> sendCode(String phone);


}