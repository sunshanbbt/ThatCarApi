package com.zhengshun.touch.api.service;

import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.TbTrip;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TbTripService extends BaseService<TbTrip, Long> {

    Boolean saveTrip(HttpServletRequest request, Date estimateDate, String plateNo, String taxiApp,Long userId, String gbs);


    Boolean updateTrip( Long id, Date estimateDate, String gbs);

    List<TbTrip> getEarlyWarnTrip();

    Boolean updateStatus(Long id, Integer scheduleStatus, String gbs);

    TbTrip getLastTrip(Long userId);

    Integer getTripCount(Long userId);
}