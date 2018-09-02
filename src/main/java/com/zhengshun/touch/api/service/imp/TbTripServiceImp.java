package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.service.impl.BaseServiceImpl;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.domain.TbTripLog;
import com.zhengshun.touch.api.mapper.TbTripLogMapper;
import com.zhengshun.touch.api.mapper.TbTripMapper;
import com.zhengshun.touch.api.service.TbTripService;
import com.zhengshun.touch.api.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("tbTripService")
public class TbTripServiceImp extends BaseServiceImpl<TbTrip, Long> implements TbTripService {

    public static final Logger logger = LoggerFactory.getLogger(TbTripServiceImp.class);
    @Autowired
    private TbTripMapper tbTripMapper;
    @Autowired
    private TbTripLogMapper tbTripLogMapper;

    @Override
    public BaseMapper<TbTrip, Long> getMapper() {
        return tbTripMapper;
    }

    @Override
    public Boolean saveTrip(HttpServletRequest request,  Date estimateDate, String plateNo, String taxiApp,Long userId, String gbs) {

        TbTrip tbTrip = new TbTrip();
        tbTrip.setEstimateDate( estimateDate );
        tbTrip.setPlateNo( plateNo );
        tbTrip.setTaxiApp( taxiApp );
        tbTrip.setScheduleStatus( 1 );
        tbTrip.setCreateDate( new Date() );
        tbTrip.setUserId( userId );
        int res = tbTripMapper.save( tbTrip );


        if ( res > 0 ) {
            TbTrip tbTrip1 = tbTripMapper.findLastTrip( userId );
            TbTripLog tbTripLog = new TbTripLog();
            tbTripLog.setTripId( tbTrip1.getId() );
            tbTripLog.setUserId( userId );
            tbTripLog.setEstimateDate( estimateDate );
            tbTripLog.setGbs( gbs );
            tbTripLog.setScheduleStatus( 1 );
            tbTripLog.setCreateDate( new Date());
            tbTripLogMapper.save( tbTripLog );
           return true;
        }

        return false;
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