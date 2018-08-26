package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.service.impl.BaseServiceImpl;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.mapper.TbTripMapper;
import com.zhengshun.touch.api.mapper.TbUserMapper;
import com.zhengshun.touch.api.service.TbTripService;
import com.zhengshun.touch.api.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TbTripServiceImp extends BaseServiceImpl<TbTrip, Long> implements TbTripService {

    public static final Logger logger = LoggerFactory.getLogger(TbTripServiceImp.class);
    @Autowired
    private TbTripMapper tbTripMapper;
    @Autowired
    private TbUserMapper tbUserMapper;


    @Override
    public BaseMapper<TbTrip, Long> getMapper() {
        return tbTripMapper;
    }

    @Override
    public Boolean saveTrip(HttpServletRequest request,  Date estimateDate, String plateNo, String taxiApp,String rdSessionKey) {

        TbTrip tbTrip = new TbTrip();
        tbTrip.setEstimateDate( estimateDate );
        tbTrip.setPlateNo( plateNo );
        tbTrip.setTaxiApp( taxiApp );
        tbTrip.setScheduleStatus( 1 );
        tbTrip.setCreateDate( new Date() );


        Map<String, Object> params = new HashMap<>();

        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            tbTrip.setUserId( tbUser.getId() );

            int res = tbTripMapper.save( tbTrip );
            if ( res > 0 ) {
               return true;
            }
        } else {
            logger.info("【TbUserServiceImp】【saveTrip】 该用户未创建session信息，请先创建session信息");
        }
        return false;
    }

    @Override
    public TbTrip getLastTrip(String rdSessionKey) {
        Map<String, Object> params = new HashMap<>();

        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            TbTrip tbTrip = tbTripMapper.findLastTrip( tbUser.getId() );
            return tbTrip;
        }
        return null;
    }

    @Override
    public Boolean updateStatus(Long id, Integer scheduleStatus, String rdSessionKey) {
        Map<String, Object> params = new HashMap<>();

        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            TbTrip tbTrip = new TbTrip();
            tbTrip.setId( id );
            tbTrip.setScheduleStatus( scheduleStatus );

            int res = tbTripMapper.update( tbTrip );
            if ( res > 0 ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateTrip(Long id, Date estimateDate, String plateNo, String taxiApp, String rdSessionKey) {
        Map<String, Object> params = new HashMap<>();

        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            TbTrip tbTrip = new TbTrip();
            tbTrip.setId( id );
            tbTrip.setEstimateDate( estimateDate );
            tbTrip.setPlateNo( plateNo );
            tbTrip.setTaxiApp( taxiApp );

            int res = tbTripMapper.update( tbTrip );
            if ( res > 0 ) {
                return true;
            }
        }
        return false;
    }
}