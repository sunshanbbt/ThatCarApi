package com.zhengshun.touch.api.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhengshun.touch.api.common.exception.ServiceException;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.service.TbSmsService;
import com.zhengshun.touch.api.service.TbTripService;
import com.zhiliao.social.manage.domain.QuartzInfo;
import com.zhiliao.social.manage.domain.QuartzLog;
import com.zhiliao.social.manage.service.QuartzInfoService;
import com.zhiliao.social.manage.service.QuartzLogService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import tool.util.BeanUtil;
import tool.util.BigDecimalUtil;
import tool.util.StringUtil;

import com.alibaba.fastjson.JSONObject;
import com.zhiliao.social.cl.domain.BankCard;
import com.zhiliao.social.cl.domain.BorrowRepay;
import com.zhiliao.social.cl.domain.PayLog;
import com.zhiliao.social.cl.model.BorrowRepayLogModel;
import com.zhiliao.social.cl.model.BorrowRepayModel;
import com.zhiliao.social.cl.model.PayLogModel;
import com.zhiliao.social.cl.model.pay.lianlian.QueryRepaymentModel;
import com.zhiliao.social.cl.model.pay.lianlian.RepaymentModel;
import com.zhiliao.social.cl.model.pay.lianlian.RiskItems;
import com.zhiliao.social.cl.model.pay.lianlian.constant.LianLianConstant;
import com.zhiliao.social.cl.model.pay.lianlian.util.LianLianHelper;
import com.zhiliao.social.cl.service.BankCardService;
import com.zhiliao.social.cl.service.BorrowRepayService;
import com.zhiliao.social.cl.service.ClBorrowService;
import com.zhiliao.social.cl.service.SoSmsService;
import com.zhiliao.social.cl.service.PayLogService;
import com.zhiliao.social.core.common.context.Global;
import com.zhiliao.social.core.common.exception.ServiceException;
import com.zhiliao.social.core.common.util.DateUtil;
import com.zhiliao.social.core.common.util.OrderNoUtil;
import com.zhiliao.social.core.domain.Borrow;
import com.zhiliao.social.core.domain.User;
import com.zhiliao.social.core.domain.UserBaseInfo;
import com.zhiliao.social.core.model.BorrowModel;
import com.zhiliao.social.core.service.SoUserService;
import com.zhiliao.social.core.service.UserBaseInfoService;


@Component
@Lazy(value = false)
public class QuartzEarlyWarn implements Job {

	private static final Logger logger = Logger.getLogger(QuartzEarlyWarn.class);

	private String earlyWarn() throws ServiceException {
		logger.info("进入预警提醒任务...");
        TbTripService tbTripService = (TbTripService) BeanUtil.getBean("tbTripService");
        TbSmsService tbSmsService = (TbSmsService) BeanUtil.getBean("tbSmsService");

        // 查询到期预警行程
		Map<String, Object> params = new HashMap<>();
        List<TbTrip> tbTripList = tbTripService.getExpireTrip( params );

		logger.info("预警提醒任务，待处理的提醒任务总数为：" + tbTripList.size());

		String quartzRemark = null;
		int succeed = 0;
		int fail = 0;
		int total = 0;
		for (TbTrip tbTrip : tbTripList) {
			logger.info("预警提醒任务，行程ID：" + tbTrip.getId() + "开始处理");
			try {
				// 查询用户、用户详情、借款及用户银行卡信息

				succeed++;
				total++;

//				//8104就是没有该还款计划的code
//				if(repayment.getRet_code().equals("8104")){
//					//重新上传还款计划
//					logger.info("借款订单号："+borrow.getId()+"无扣款计划信息，重新生成还款计划");
//					borrowRepayService.authSignApply(borrowRepay.getUserId());
//				}
			} catch (Exception e) {
				fail++;
				total++;
				logger.error(e.getMessage(), e);
			}
		}

		quartzRemark = "处理总数"+total+"个，成功"+succeed+"个，失败"+fail+"个";
		logger.info("代扣还款任务，执行完毕，" + quartzRemark);
		return quartzRemark;

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
		QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
		// 查询当前任务信息
		QuartzInfo quartzInfo = quartzInfoService.findByCode("doRepayment");
		Map<String, Object> qiData = new HashMap<>();
		qiData.put("id", quartzInfo.getId());

		QuartzLog quartzLog = new QuartzLog();
		quartzLog.setQuartzId(quartzInfo.getId());
		quartzLog.setStartTime(DateUtil.getNow());
		try {
			String remark = repayment();
			quartzLog.setTime(DateUtil.getNow().getTime() - quartzLog.getStartTime().getTime());
			quartzLog.setResult("10");
			quartzLog.setRemark(remark);
			qiData.put("succeed", quartzInfo.getSucceed() + 1);
		} catch (Exception e) {
			quartzLog.setResult("20");
			qiData.put("fail", quartzInfo.getFail() + 1);
			logger.error(e.getMessage(), e);
		} finally {
			logger.info("保存代扣还款定时任务执行记录");
			quartzLogService.save(quartzLog);
			quartzInfoService.update(qiData);
		}

	}
}