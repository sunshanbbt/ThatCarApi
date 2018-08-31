package com.zhengshun.touch.api.mapper;

import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.mapper.RDBatisDao;
import com.zhengshun.touch.api.domain.QuartzLog;
import org.apache.ibatis.annotations.Param;



@RDBatisDao
public interface QuartzLogMapper extends BaseMapper<QuartzLog,Long> {


}
