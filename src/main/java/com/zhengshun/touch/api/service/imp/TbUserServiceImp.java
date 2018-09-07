package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.common.MsgUtils;
import com.zhengshun.touch.api.common.constans.TripScheduleStatusEnum;
import com.zhengshun.touch.api.common.context.Constant;
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
import tool.util.StringUtil;

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
    public Map<String, Object> updateUnlockPwd(TbUser tbUser, String unlockPwd) {
        Map<String, Object> retMap = new HashMap<>();
        String salt = tbUser.getSalt();
        logger.info(" tbuser salt = " + salt);
        if (StringUtil.isBlank( salt )) {
            salt = PasswordUtil.salt();
        }
        logger.info(" salt = " + salt + ", unlockPwd = " + tbUser.getUnlockPwd() + " unlockPwd = " +  PasswordUtil.generate( unlockPwd, salt ));
        if ( tbUser.getRiskPwd().equals( PasswordUtil.generate( unlockPwd, salt ))) {
            retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.UNLOCK_PWD_REPEAT);
        } else {
            int res = tbUserMapper.updateUnlockPwd( tbUser.getId(), PasswordUtil.generate( unlockPwd, salt), salt );
            if ( res > 0 ) {
                retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
            } else {
                retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_FAIL_MSG);
            }
        }
        return retMap;
    }

    @Override
    public Map<String, Object> updateRiskPwd(TbUser tbUser, String riskPwd) {
        Map<String, Object> retMap = new HashMap<>();
        String salt = tbUser.getSalt();
        if (StringUtil.isBlank( salt )) {
            salt = PasswordUtil.salt();
        }
        logger.info(" salt = " + salt + ", unlockPwd = " + tbUser.getUnlockPwd() + " riskPwd = " +  PasswordUtil.generate( riskPwd, salt ));
        if ( tbUser.getUnlockPwd().equals( PasswordUtil.generate( riskPwd, salt ))) {
            retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.RISK_PWD_REPEAT);
        } else {
            int res = tbUserMapper.updateRiskPwd( tbUser.getId(), PasswordUtil.generate( riskPwd, salt), salt );
            if ( res > 0 ) {
                retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
            } else {
                retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_FAIL_MSG);
            }
        }
        return retMap;
    }

    @Override
    public Map<String, Object> verifyPwd(TbUser tbUser,String pwd) {

        Map<String, Object> retMap = new HashMap<>();
        if ( tbUser.getUnlockPwd().equals( PasswordUtil.generate(pwd, tbUser.getSalt()))) {
            retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
        } else if ( tbUser.getRiskPwd().equals( PasswordUtil.generate(pwd, tbUser.getSalt()))) {
                TbTrip tbTrip = tbTripService.getLastTrip( tbUser.getId() );
                if ( tbTrip == null ) {
                    retMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                    retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.TRIP_NOT_EXIST);
                } else {
                    Integer count = tbSmsService.selectByTripId( tbTrip.getId() );
                    if ( count > 0 ) {
                        retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                        retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
                    } else {
                        List<TbEmerContact> emerContactList = tbUserEmerContactService.getListByUser(tbUser.getId());
                        for (TbEmerContact tbEmerContact : emerContactList) {
                            Boolean falg = tbSmsService.sendAutoEarlyWarn(tbEmerContact.getPhone(), tbUser.getRealName
                                    (), tbTrip.getTaxiApp(), tbTrip.getPlateNo(), tbTrip.getId());
                        }
                        retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                        retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
                    }
                }
        }
        return retMap;
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