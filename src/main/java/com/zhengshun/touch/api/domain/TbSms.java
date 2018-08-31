package com.zhengshun.touch.api.domain;


import java.io.Serializable;
import java.util.Date;

/**
 *
 * This class corresponds to the database table tb_sms
 *
 */
@SuppressWarnings("serial")
public class TbSms implements Serializable{

    private Long id;

    private String phone;

    private Date sendTime;

    private String content;

    private String platform;

    private Date respTime;

    private String resp;

    private String smsType;

    private String code;

    private String orderNo;

    private String state;

    private Integer verifyTime;

    public TbSms(String phone, Date sendTime, String content, String code, String state, String smsType, String platform) {
        this.phone = phone;
        this.sendTime = sendTime;
        this.content = content;
        this.code = code;
        this.state = state;
        this.smsType = smsType;
        this.platform = platform;
    }
    public TbSms(String phone, Date sendTime, String content, String state, String smsType, String platform) {
        this.phone = phone;
        this.sendTime = sendTime;
        this.content = content;
        this.state = state;
        this.smsType = smsType;
        this.platform = platform;
    }
    public TbSms() {
    }

    public TbSms(Long id, String phone, Date sendTime, String content, Date respTime, String resp, String smsType, String code, String orderNo, String state, Integer verifyTime, String platform) {
        this.id = id;
        this.phone = phone;
        this.sendTime = sendTime;
        this.content = content;
        this.platform = platform;
        this.respTime = respTime;
        this.resp = resp;
        this.smsType = smsType;
        this.code = code;
        this.orderNo = orderNo;
        this.state = state;
        this.verifyTime = verifyTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Integer verifyTime) {
        this.verifyTime = verifyTime;
    }
}