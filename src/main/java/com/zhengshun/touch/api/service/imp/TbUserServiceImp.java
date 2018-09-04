package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.common.constans.TripScheduleStatusEnum;
import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.service.impl.BaseServiceImpl;
import com.zhengshun.touch.api.common.util.PasswordUtil;
import com.zhengshun.touch.api.domain.TbEmerContact;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.mapper.TbUserMapper;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.service.TbSmsService;
import com.zhengshun.touch.api.service.TbTripService;
import com.zhengshun.touch.api.service.TbUserEmerContactService;
import com.zhengshun.touch.api.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("tbUserService")
public class TbUserServiceImp extends BaseServiceImpl<TbUser, Long> implements TbUserService {

    public static final Logger logger = LoggerFactory.getLogger(TbUserServiceImp.class);
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbSmsService tbSmsService;
    @Autowired
    private TbUserEmerContactService tbUserEmerContactService;
    @Autowired
    private TbTripService tbTripService;

    @Override
    public BaseMapper<TbUser, Long> getMapper() {
        return tbUserMapper;
    }

    @Override
    public Boolean saveUser(HttpServletRequest request, String avatarUrl, String city, String country, Integer gender,
                      String language, String nickName, Long userId, String province) {

        TbUser tbUser1 = new TbUser();
        tbUser1.setAvatarUrl( avatarUrl );
        tbUser1.setNickName( nickName );
        tbUser1.setProvince( province );
        tbUser1.setCity( city );
        tbUser1.setCountry( country );
        tbUser1.setGender( gender );
        tbUser1.setLanguage( language );
        tbUser1.setId( userId );
        int res = tbUserMapper.update( tbUser1 );
        if ( res > 0 ) {
            logger.info("【TbUserServiceImp】【saveUser】更新用户详细信息，更新成功 userId = " + userId);
            return true;
        }
        return false;

    }


    @Override
    public Boolean updateUnlockPwd(HttpServletRequest request, String unlockPwd,Long userId) {
        int res = tbUserMapper.updateUnlockPwd( userId, PasswordUtil.generate(unlockPwd) );
        if ( res > 0 ){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateRiskPwd(HttpServletRequest request, String riskPwd, Long userId) {
        int res = tbUserMapper.updateRiskPwd( userId, PasswordUtil.generate(riskPwd) );
        if ( res > 0 ){
            return true;
        }
        return false;
    }

    @Override
    public Boolean verifyPwd(String pwd, TbUser tbUser) {
        if ( PasswordUtil.verify( pwd , tbUser.getUnlockPwd() )) {
            return true;
        }
        if ( PasswordUtil.verify( pwd, tbUser.getRiskPwd())) {

                // 查询用户的紧急联系人
                TbTrip tbTrip = tbTripService.getLastTrip( tbUser.getId() );
                Integer count = tbSmsService.selectByTripId( tbTrip.getId() );
                if ( count > 0 ) {
                    logger.info("【QuartzEarlyWarn】【earlyWarn】 该用户已发过预警 userId = " + tbTrip.getUserId() + "， tripId = " + tbTrip.getId());
                    return true;
                } else {
                    if (tbTrip != null) {
                        List<TbEmerContact> emerContactList = tbUserEmerContactService.getListByUser(tbUser.getId());
                        for (TbEmerContact tbEmerContact : emerContactList) {
                            Boolean falg = tbSmsService.sendAutoEarlyWarn(tbEmerContact.getPhone(), tbUser.getRealName
                                    (), tbTrip.getTaxiApp(), tbTrip.getPlateNo(), tbTrip.getId());
//                        if (falg) {
////                            tbTripService.updateStatus( tbTrip.getId(), TripScheduleStatusEnum.OVER_TIME.code );
////                        }
                        }
                    }
                }
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateInfo(String wxNo, String realName, String phone, Long userId) {
        TbUser tbUser1 = new TbUser();
        tbUser1.setWxNo( wxNo );
        tbUser1.setRealName( realName );
        tbUser1.setPhone( phone );
        tbUser1.setId( userId );
        int res = tbUserMapper.update( tbUser1 );
        if ( res > 0 ) {
            return true;
        }
        return false;
    }

    @Override
    public TbUser getUserByRdSessionKey(String rdSessionKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        return tbUser;
    }

    @Override
    public TbUser getUserById(Long id) {
        return tbUserMapper.findByPrimary( id );
    }
}