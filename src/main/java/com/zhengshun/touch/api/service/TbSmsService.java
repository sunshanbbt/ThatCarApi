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

    /**
     * 发送超时预警短信
     * @param phone
     * @return
     */
    Boolean sendOverTimeEarlyWarn(String phone, String name, String plateNo, String taxiApp);

    /**
     * 发送自主预警短信
     * @param phone
     * @param name
     * @param plate_no
     * @param taxiApp
     * @return
     */
    Boolean sendAutoEarlyWarn(String phone, String name, String plateNo, String taxiApp);
}