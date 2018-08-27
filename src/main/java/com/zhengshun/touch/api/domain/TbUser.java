package com.zhengshun.touch.api.domain;


import java.io.Serializable;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table tb_user
 *
 * @mbg.generated do_not_delete_during_merge
 */
@SuppressWarnings("serial")
public class TbUser implements Serializable{
    private Long id;

    /**
     * Database Column Remarks:
     *   用户头像地址
     */
    private String avatarUrl;

    /**
     * Database Column Remarks:
     *   用户所在城市
     */
    private String city;

    /**
     * Database Column Remarks:
     *   用户所在国家
     */
    private String country;

    /**
     * Database Column Remarks:
     *   用户性别 0：未知、1：男、2：女
     */
    private Integer gender;

    /**
     * Database Column Remarks:
     *   显示 country province city 所用的语言
     */
    private String language;

    /**
     * Database Column Remarks:
     *   用户昵称
     */
    private String nickName;

    /**
     * Database Column Remarks:
     *   用户 openId
     */
    private String openId;

    /**
     * Database Column Remarks:
     *   用户所在省份
     */
    private String province;

    /**
     * Database Column Remarks:
     *   用户状态 0 停用 1 正常
     */
    private Integer status;

    /**
     * Database Column Remarks:
     *   是否删除 0 否 1 是
     */
    private Integer deleteFlag;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private Date createDate;

    /**
     * Database Column Remarks:
     *   修改时间
     */
    private Date updateDate;

    private String sessionKey;

    private String rdSessionKey;

    private String uuid;

    private String phone;

    private String unlockPwd;

    private String riskPwd;

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

    public String getUnlockPwd() {
        return unlockPwd;
    }

    public void setUnlockPwd(String unlockPwd) {
        this.unlockPwd = unlockPwd;
    }

    public String getRiskPwd() {
        return riskPwd;
    }

    public void setRiskPwd(String riskPwd) {
        this.riskPwd = riskPwd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getRdSessionKey() {
        return rdSessionKey;
    }

    public void setRdSessionKey(String rdSessionKey) {
        this.rdSessionKey = rdSessionKey;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}