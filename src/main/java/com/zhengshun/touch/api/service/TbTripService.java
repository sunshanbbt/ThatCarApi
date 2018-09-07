package com.zhengshun.touch.api.service;

import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.domain.TbUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TbTripService extends BaseService<TbTrip, Long> {

    Map<String, Object> saveTrip(TbUser tbUser, Date estimateDate, String plateNo, String taxiApp,String gbs);

    Boolean updateTrip( Long id, Date estimateDate, String gbs);

    List<TbTrip> getEarlyWarnTrip();

    Boolean updateStatus(Long id, Integer scheduleStatus, String gbs);

    TbTrip getLastTrip(Long userId);

    Integer getTripCount(Long userId);
}