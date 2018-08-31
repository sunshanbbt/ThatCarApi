package com.zhengshun.touch.api.service;

import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.QuartzInfo;

import java.util.List;
import java.util.Map;


public interface QuartzInfoService extends BaseService<QuartzInfo, Long> {


	/**
	 * 保存定时任务数据
	 * @param qi
	 */
	boolean save(QuartzInfo qi);

	/**
	 * 修改定时任务
	 * @param search
	 * @return
	 */
	boolean update(Map<String, Object> search);

	/**
	 * 查询所有任务
	 * @param result
	 * @return
	 */
	List<QuartzInfo> list(Map<String, Object> result);

//	/**
//	 * 定时任务分页查询
//	 * @param searchMap
//	 * @param current
//	 * @param pageSize
//	 * @return
//	 */
//	Page<QuartzInfoModel> page(Map<String, Object> searchMap, int current,
//                               int pageSize);
//
	/**
	 * 据任务标识查询任务
	 * @param paramMap
	 * @return
	 */
	QuartzInfo findByCode(String code);
	
	/**
	 * 据条件查询定时任务详情
	 * @param paramMap
	 * @return
	 */
	QuartzInfo findSelective(Map<String, Object> paramMap);
	
}
