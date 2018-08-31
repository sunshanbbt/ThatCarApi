package com.zhengshun.touch.api.service;


import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.QuartzLog;

public interface QuartzLogService extends BaseService<QuartzLog, Long> {

	/**
	 * 保存日志
	 */
	int save(QuartzLog ql);

//	Page<QuartzLogModel> page(Map<String, Object> searchMap, int current,
//                              int pageSize);

}
