package com.zhengshun.touch.api.service.imp;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.service.impl.BaseServiceImpl;
import com.zhengshun.touch.api.common.util.PasswordUtil;
import com.zhengshun.touch.api.mapper.TbUserMapper;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TbUserServiceImp extends BaseServiceImpl<TbUser, Long> implements TbUserService {

    public static final Logger logger = LoggerFactory.getLogger(TbUserServiceImp.class);
    @Autowired
    private TbUserMapper tbUserMapper;


    @Override
    public BaseMapper<TbUser, Long> getMapper() {
        return tbUserMapper;
    }

    @Override
    public Boolean saveUser(HttpServletRequest request, String avatarUrl, String city, String country, Integer gender,
                      String language, String nickName, String rdSessionKey, String province) {

        TbUser tbUser1 = new TbUser();
        tbUser1.setAvatarUrl( avatarUrl );
        tbUser1.setNickName( nickName );
        tbUser1.setProvince( province );
        tbUser1.setCity( city );
        tbUser1.setCountry( country );
        tbUser1.setGender( gender );
        tbUser1.setLanguage( language );
        Map<String, Object> params = new HashMap<>();

        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            logger.info("【TbUserServiceImp】【saveUser】 该用户已创建session信息，更新用户详细信息....");
            tbUser1.setId( tbUser.getId() );
            int res = tbUserMapper.update( tbUser1 );
            if ( res > 0 ) {
                logger.info("【TbUserServiceImp】【saveUser】 该用户已创建session信息，更新用户详细信息，更新成功 userId = " + tbUser.getId());
            } else {
                logger.info("【TbUserServiceImp】【saveUser】 该用户已创建session信息，更新用户详细信息，更新失败 userId = " + tbUser.getId());
                return false;
            }
        } else {
            logger.info("【TbUserServiceImp】【saveUser】 该用户未创建session信息，请先创建session信息");
            return false;
        }
        return true;
    }


    @Override
    public Boolean updateUnlockPwd(HttpServletRequest request, String unlockPwd, String rdSessionKey) {
        Map<String, Object> params = new HashMap<>();

        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            TbUser tbUser1 = new TbUser();
            tbUser1.setId( tbUser.getId() );
            tbUser1.setUnlockPwd(PasswordUtil.generate(unlockPwd) );
            int res = tbUserMapper.update( tbUser1 );

            if ( res > 0 ){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateRiskPwd(HttpServletRequest request, String riskPwd, String rdSessionKey) {
        Map<String, Object> params = new HashMap<>();

        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            TbUser tbUser1 = new TbUser();
            tbUser1.setId( tbUser.getId() );
            tbUser1.setRiskPwd(PasswordUtil.generate(riskPwd) );
            int res = tbUserMapper.update( tbUser1 );

            if ( res > 0 ){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean verifyUnlockPwd(HttpServletRequest request, String unlockPwd, String rdSessionKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            if ( PasswordUtil.verify( unlockPwd , tbUser.getUnlockPwd() )) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean verifyRiskPwd(HttpServletRequest request, String riskPwd, String rdSessionKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("rdSessionKey", rdSessionKey );
        TbUser tbUser = tbUserMapper.findSelective(params);
        if ( tbUser != null ) {
            if ( PasswordUtil.verify( riskPwd , tbUser.getRiskPwd() )) {
                return true;
            }
        }
        return false;
    }
}