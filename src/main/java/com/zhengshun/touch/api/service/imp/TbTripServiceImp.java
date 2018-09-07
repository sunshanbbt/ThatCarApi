package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.common.MsgUtils;
import com.zhengshun.touch.api.common.context.Constant;
import com.zhengshun.touch.api.common.context.Global;
import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.service.impl.BaseServiceImpl;
import com.zhengshun.touch.api.common.util.StringUtil;
import com.zhengshun.touch.api.domain.TbEmerContact;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.domain.TbTripLog;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.mapper.TbTripLogMapper;
import com.zhengshun.touch.api.mapper.TbTripMapper;
import com.zhengshun.touch.api.service.TbTripService;
import com.zhengshun.touch.api.service.TbUserEmerContactService;
import com.zhengshun.touch.api.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("tbTripService")
public class TbTripServiceImp extends BaseServiceImpl<TbTrip, Long> implements TbTripService {

    public static final Logger logger = LoggerFactory.getLogger(TbTripServiceImp.class);
    @Autowired
    private TbTripMapper tbTripMapper;
    @Autowired
    private TbTripLogMapper tbTripLogMapper;
    @Autowired
    private TbUserEmerContactService tbUserEmerContactService;

    @Override
    public BaseMapper<TbTrip, Long> getMapper() {
        return tbTripMapper;
    }

    @Override
    public Map<String, Object> saveTrip(TbUser tbUser, Date estimateDate, String plateNo, String taxiApp,String gbs) {
        Map<String, Object> retMap = new HashMap<>();
        if (StringUtil.isBlank( tbUser.getUnlockPwd() )) {
            retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_UNLOCK);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.UNLOCK_NOT_NULL);
            return retMap;
        }
        if (StringUtil.isBlank( tbUser.getRiskPwd() )) {
            retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_RISK);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.RISK_NOT_NULL);
            return retMap;
        }
        if (StringUtil.isBlank( tbUser.getRealName() )) {
            retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_NAME);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.NAME_NOT_NULL);
            return retMap;
        }
        List<TbEmerContact> emerContactList = tbUserEmerContactService.getListByUser( tbUser.getId() );
        if ( emerContactList == null || emerContactList.size() == 0) {
            retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_CONTACT);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.CONTANS_NOT_NULL);
            return retMap;
        }
        Integer count = this.getTripCount( tbUser.getId() );
        if ( count >= Global.getInt("trip_count")) {
            retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.TRIP_LIMIT);
            return retMap;
        }
        TbTrip tbTrip = new TbTrip();
        tbTrip.setEstimateDate( estimateDate );
        tbTrip.setPlateNo( plateNo );
        tbTrip.setTaxiApp( taxiApp );
        tbTrip.setScheduleStatus( 1 );
        tbTrip.setCreateDate( new Date() );
        tbTrip.setUserId( tbUser.getId() );
        int res = tbTripMapper.save( tbTrip );
        if ( res > 0 ) {
            TbTrip tbTrip1 = tbTripMapper.findLastTrip( tbUser.getId() );
            TbTripLog tbTripLog = new TbTripLog();
            tbTripLog.setTripId( tbTrip1.getId() );
            tbTripLog.setUserId( tbUser.getId() );
            tbTripLog.setEstimateDate( estimateDate );
            tbTripLog.setGbs( gbs );
            tbTripLog.setScheduleStatus( 1 );
            tbTripLog.setCreateDate( new Date());
            tbTripLogMapper.save( tbTripLog );
            retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
            return retMap;
        }
        retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
        retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_FAIL_MSG);
        return retMap;
    }


    @Override
    public TbTrip getLastTrip(Long userId) {
        return tbTripMapper.findLastTrip(userId );
    }


    @Override
    public Boolean updateTrip(Long id, Date estimateDate, String gbs) {
        TbTrip tbTrip = new TbTrip();
        tbTrip.setId( id );
        tbTrip.setEstimateDate( estimateDate );
        int res = tbTripMapper.update( tbTrip );
        if ( res > 0 ) {
            TbTrip tbTrip1 = tbTripMapper.findByPrimary( id );
            TbTripLog tbTripLog = new TbTripLog();
            tbTripLog.setTripId( tbTrip1.getId() );
            tbTripLog.setUserId( tbTrip1.getUserId() );
            tbTripLog.setEstimateDate( estimateDate);
            tbTripLog.setGbs( gbs );
            tbTripLog.setScheduleStatus( tbTrip1.getScheduleStatus() );
            tbTripLog.setCreateDate( new Date());
            tbTripLogMapper.save( tbTripLog );
            return true;
        }
        return false;
    }

    @Override
    public List<TbTrip> getEarlyWarnTrip() {
        return tbTripMapper.getEarlyWarnTrip();
    }

    @Override
    public Boolean updateStatus(Long id, Integer scheduleStatus, String gbs) {
        int res = tbTripMapper.updateScheduleStatus( id, scheduleStatus );
        if ( res > 0 ) {
            TbTrip tbTrip1 = tbTripMapper.findByPrimary( id );
            TbTripLog tbTripLog = new TbTripLog();
            tbTripLog.setTripId( tbTrip1.getId() );
            tbTripLog.setUserId( tbTrip1.getUserId() );
            tbTripLog.setEstimateDate( tbTrip1.getEstimateDate() );
            tbTripLog.setGbs( gbs );
            tbTripLog.setScheduleStatus( scheduleStatus );
            tbTripLog.setCreateDate( new Date());
            tbTripLogMapper.save( tbTripLog );
            return true;
        }
        return false;
    }

    @Override
    public Integer getTripCount(Long userId) {
        return tbTripMapper.getTripCount( userId );
    }
}