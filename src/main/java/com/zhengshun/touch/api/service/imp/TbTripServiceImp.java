package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.service.impl.BaseServiceImpl;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.domain.TbUser;
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
import java.util.Map;

@Service
public class TbTripServiceImp extends BaseServiceImpl<TbTrip, Long> implements TbTripService {

    public static final Logger logger = LoggerFactory.getLogger(TbTripServiceImp.class);
    @Autowired
    private TbTripMapper tbTripMapper;
    @Autowired
    private TbUserService tbUserService;

    @Override
    public BaseMapper<TbTrip, Long> getMapper() {
        return tbTripMapper;
    }

    @Override
    public Boolean saveTrip(HttpServletRequest request,  Date estimateDate, String plateNo, String taxiApp,Long userId) {

        TbTrip tbTrip = new TbTrip();
        tbTrip.setEstimateDate( estimateDate );
        tbTrip.setPlateNo( plateNo );
        tbTrip.setTaxiApp( taxiApp );
        tbTrip.setScheduleStatus( 1 );
        tbTrip.setCreateDate( new Date() );
        tbTrip.setUserId( userId );
        int res = tbTripMapper.save( tbTrip );
        if ( res > 0 ) {
           return true;
        }

        return false;
    }


    @Override
    public TbTrip getLastTrip(Long userId) {
        return tbTripMapper.findLastTrip(userId );
    }


    @Override
    public Boolean updateTrip(Long id, Date estimateDate, String plateNo, String taxiApp) {
        TbTrip tbTrip = new TbTrip();
        tbTrip.setId( id );
        tbTrip.setEstimateDate( estimateDate );
        tbTrip.setPlateNo( plateNo );
        tbTrip.setTaxiApp( taxiApp );
        int res = tbTripMapper.update( tbTrip );
        if ( res > 0 ) {
            return true;
        }
        return false;
    }

    @Override
    public List<TbTrip> getEarlyWarnTrip() {
        return tbTripMapper.getEarlyWarnTrip();
    }

    @Override
    public Boolean updateStatus(Long id, Integer scheduleStatus) {
        int res = tbTripMapper.updateScheduleStatus( id, scheduleStatus );
        if ( res > 0 ) {
            return true;
        }
        return false;
    }
}