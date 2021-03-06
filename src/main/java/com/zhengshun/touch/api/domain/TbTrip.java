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
public class TbTrip implements Serializable{
    private Long id;

    private Long userId;

    private Date estimateDate;

    private String plateNo;

    private String taxiApp;

    private Integer scheduleStatus;

    private Integer remainMinutes;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getTaxiApp() {
        return taxiApp;
    }

    public void setTaxiApp(String taxiApp) {
        this.taxiApp = taxiApp;
    }

    public Integer getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(Integer scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getRemainMinutes() {
        return remainMinutes;
    }

    public void setRemainMinutes(Integer remainMinutes) {
        this.remainMinutes = remainMinutes;
    }
}