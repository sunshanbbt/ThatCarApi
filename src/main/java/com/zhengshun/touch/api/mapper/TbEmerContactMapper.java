package com.zhengshun.touch.api.mapper;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.mapper.RDBatisDao;
import com.zhengshun.touch.api.domain.TbEmerContact;
import com.zhengshun.touch.api.domain.TbTrip;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

@RDBatisDao
public interface TbEmerContactMapper extends BaseMapper<TbEmerContact, Long> {

    int deleteById( @Param("id") Long id );

    int countByPhone(@Param("userId") Long userId, @Param("phone") String phone);
}