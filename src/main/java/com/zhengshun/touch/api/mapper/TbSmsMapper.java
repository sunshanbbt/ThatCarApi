package com.zhengshun.touch.api.mapper;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.mapper.RDBatisDao;
import com.zhengshun.touch.api.domain.TbSms;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@RDBatisDao
public interface TbSmsMapper extends BaseMapper<TbSms, Long> {

    /**
     * 查询最新一条短信记录
     * @param data
     * @return
     */
    TbSms findTimeMsg(Map<String, Object> data);

    Integer selectByTripId(@Param("tripId") Long tripId);
}