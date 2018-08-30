package com.zhengshun.touch.api.service;

import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.TbTrip;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TbTripService extends BaseService<TbTrip, Long> {

    Boolean saveTrip(HttpServletRequest request, Date estimateDate, String plateNo, String taxiApp,String rdSessionKey);

    TbTrip getLastTrip(String rdSessionKey);

    Boolean updateStatus(Long id,  Integer scheduleStatus, String rdSessionKey);

    Boolean updateTrip( Long id, Date estimateDate, String plateNo, String taxiApp,String rdSessionKey );

    List<TbTrip> getExpireTrip( Map<String, Object> params);
}