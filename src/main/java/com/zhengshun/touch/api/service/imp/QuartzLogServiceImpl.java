package com.zhengshun.touch.api.service.imp;


import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.service.impl.BaseServiceImpl;
import com.zhengshun.touch.api.domain.QuartzLog;
import com.zhengshun.touch.api.mapper.QuartzLogMapper;
import com.zhengshun.touch.api.service.QuartzLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;



@SuppressWarnings("unused")
@Service("quartzLogService")
public class QuartzLogServiceImpl extends BaseServiceImpl<QuartzLog, Long> implements QuartzLogService {
	
    
	private static final Logger logger = LoggerFactory.getLogger(QuartzLogServiceImpl.class);
   
    @Resource
    private QuartzLogMapper quartzLogMapper;




	@Override
	public BaseMapper<QuartzLog, Long> getMapper() {
		return quartzLogMapper;
	}




	@Override
	public int save(QuartzLog ql) {
		return quartzLogMapper.save(ql);
	}



//
//	@Override
//	public Page<QuartzLogModel> page(Map<String, Object> searchMap,
//			int current, int pageSize) {
//		PageHelper.startPage(current, pageSize);
//		List<QuartzLogModel> list = quartzLogMapper.page(searchMap);
//		return (Page<QuartzLogModel>)list;
//	}
	
}