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
public class TbTripLog implements Serializable{
    private Long id;

    private Long userId;

    private Long tripId;

    private Date estimateDate;

    private String gbs;

    private Integer scheduleStatus;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getGbs() {
        return gbs;
    }

    public void setGbs(String gbs) {
        this.gbs = gbs;
    }

    public Integer getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(Integer scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}