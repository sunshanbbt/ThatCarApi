package com.zhengshun.touch.api.mapper;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.mapper.RDBatisDao;
import com.zhengshun.touch.api.domain.TbTrip;
import org.apache.ibatis.annotations.Param;

@RDBatisDao
public interface TbTripMapper extends BaseMapper<TbTrip, Long> {

    TbTrip findLastTrip( @Param("userId") Long userId );

    int updateScheduleStatus( @Param("id") Long id, @Param("scheduleStatus") Integer scheduleStatus);
}