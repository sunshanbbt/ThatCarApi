package com.zhengshun.touch.api.service;



public interface TbSmsService {

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    Boolean sendCode(String phone, String type);

    /**
     * 获取验证码
     * @param phone
     * @param type
     * @return
     */
    String getCode(String phone, String type);
}