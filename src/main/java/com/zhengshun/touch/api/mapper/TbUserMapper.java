package com.zhengshun.touch.api.mapper;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.mapper.RDBatisDao;
import com.zhengshun.touch.api.domain.TbUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

@RDBatisDao
public interface TbUserMapper extends BaseMapper<TbUser, Long> {

    int insert(TbUser tbUser);

    int updateUnlockPwd(@Param("id") Long id, @Param("unlockPwd") String unlockPwd, @Param("salt") String salt );

    int updateRiskPwd(@Param("id") Long id, @Param("riskPwd") String riskPwd, @Param("salt") String salt );
}