package com.zhengshun.touch.api.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhengshun.touch.api.common.constans.TripScheduleStatusEnum;
import com.zhengshun.touch.api.common.exception.ServiceException;
import com.zhengshun.touch.api.domain.*;
import com.zhengshun.touch.api.service.*;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import tool.util.BeanUtil;
import tool.util.DateUtil;


@Component
@Lazy(value = false)
public class QuartzEarlyWarn implements Job {

	private static final Logger logger = Logger.getLogger(QuartzEarlyWarn.class);

	private String earlyWarn() throws ServiceException {
		logger.info("进入预警提醒任务...");
        TbTripService tbTripService = (TbTripService) BeanUtil.getBean("tbTripService");
        TbUserService tbUserService = (TbUserService) BeanUtil.getBean("tbUserService");
		TbUserEmerContactService tbUserEmerContactService = (TbUserEmerContactService) BeanUtil.getBean
				("tbUserEmerContactService");
        TbSmsService tbSmsService = (TbSmsService) BeanUtil.getBean("tbSmsService");

        // 查询到期预警行程
        List<TbTrip> tbTripList = tbTripService.getEarlyWarnTrip();

		logger.info("预警提醒任务，待处理的提醒任务总数为：" + tbTripList.size());

		String quartzRemark = null;
		int succeed = 0;
		int fail = 0;
		int total = 0;
		for (TbTrip tbTrip : tbTripList) {
			logger.info("预警提醒任务，行程ID：" + tbTrip.getId() + "开始处理");
			try {
				//查询用户
				TbUser tbUser = tbUserService.getUserById( tbTrip.getUserId() );
				if ( tbUser != null ) {
					Integer count = tbSmsService.selectByTripId( tbTrip.getId() );
					if ( count > 0 ) {
						logger.info("【QuartzEarlyWarn】【earlyWarn】 该用户已发过预警 userId = " + tbTrip.getUserId() + "， tripId = " + tbTrip.getId());
						continue;
					}
					// 查询用户的紧急联系人
					List<TbEmerContact> emerContactList = tbUserEmerContactService.getListByUser( tbTrip.getUserId() );
					for ( TbEmerContact tbEmerContact : emerContactList ) {
						Boolean falg = tbSmsService.sendOverTimeEarlyWarn( tbEmerContact.getPhone(), tbUser.getRealName
								(), tbTrip.getTaxiApp(), tbTrip.getPlateNo() , tbTrip.getId());
						if (falg) {
							tbTripService.updateStatus( tbTrip.getId(), TripScheduleStatusEnum.OVER_TIME.code ,"0000000");
							succeed++;
							total++;
						} else {
							fail++;
							total++;
						}
					}
				} else {
					logger.info("【QuartzEarlyWarn】【earlyWarn】 未找到该用户信息 userId = " + tbTrip.getUserId());
				}

			} catch (Exception e) {
				fail++;
				total++;
				logger.error(e.getMessage(), e);
			}
		}

		quartzRemark = "处理总数"+total+"个，成功"+succeed+"个，失败"+fail+"个";
		logger.info("预警提醒任务，执行完毕，" + quartzRemark);
		return quartzRemark;

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
		QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
		// 查询当前任务信息
		QuartzInfo quartzInfo = quartzInfoService.findByCode("doEarlyWarn");
		Map<String, Object> qiData = new HashMap<>();
		qiData.put("id", quartzInfo.getId());

		QuartzLog quartzLog = new QuartzLog();
		quartzLog.setQuartzId(quartzInfo.getId());
		quartzLog.setStartTime(DateUtil.getNow());
		try {
			String remark = earlyWarn();
			quartzLog.setTime(DateUtil.getNow().getTime() - quartzLog.getStartTime().getTime());
			quartzLog.setResult("10");
			quartzLog.setRemark(remark);
			qiData.put("succeed", quartzInfo.getSucceed() + 1);
		} catch (Exception e) {
			quartzLog.setResult("20");
			qiData.put("fail", quartzInfo.getFail() + 1);
			logger.error(e.getMessage(), e);
		} finally {
			logger.info("保存预警提醒任务定时任务执行记录");
			quartzLogService.save(quartzLog);
			quartzInfoService.update(qiData);
		}

	}
}