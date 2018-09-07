package com.zhengshun.touch.api.controller;

import com.zhengshun.touch.api.common.BaseResponse;
import com.zhengshun.touch.api.common.MsgUtils;
import com.zhengshun.touch.api.common.context.Constant;
import com.zhengshun.touch.api.common.context.Global;
import com.zhengshun.touch.api.common.util.DateUtil;
import com.zhengshun.touch.api.common.util.ServletUtils;
import com.zhengshun.touch.api.common.web.controller.BaseController;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.service.TbTripService;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TbTripController extends BaseController {


    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private TbTripService tbTripService;

    /**
     * 保存行程信息
     * @param estimateDate
     * @param plateNo
     * @param taxiApp
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/saveTrip.htm" )
    public void saveUser (
            @RequestParam( value = "estimateDate") Integer estimateDate,
            @RequestParam( value = "plateNo") String plateNo,
            @RequestParam( value = "taxiApp") String taxiApp,
            @RequestParam( value = "gbs", required = false) String gbs)
            throws Exception {

        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            logger.info( "【/api/user/saveTrip.htm】【inputs】 estimateDate = " + estimateDate + ", plateNo = " + plateNo + ", taxiApp = " + taxiApp + "， userId = " + tbUser.getId());
            Map<String, Object> retMap = tbTripService.saveTrip( tbUser, DateUtil.dateAddMins(new Date(), estimateDate), plateNo, taxiApp, gbs );
            logger.info("【/api/user/saveTrip.htm】【outputs】 " + com.zhengshun.touch.api.common.util.ConvertUtils.convertMapToString(retMap));
            ServletUtils.writeToResponse(response, retMap);
        } else {
            logger.info("【/api/user/saveTrip.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }


    }

    /**
     * 获取行程信息
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/getTrip.htm" )
    public void getTrip ()throws Exception {
        logger.info("【/api/user/getTrip.htm】 【inputs】");
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            TbTrip tbTrip = tbTripService.getLastTrip(tbUser.getId());
            if ( tbTrip != null ) {
                tbTrip.setRemainMinutes(DateUtil.minuteBetween(new Date(), tbTrip.getEstimateDate()));
            }
            Map<String, Object> retMap = new HashMap<>();

            retMap.put(Constant.RESPONSE_DATA, tbTrip);
            retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
            logger.info("【/api/user/getTrip.htm】【outputs】 " + com.zhengshun.touch.api.common.util.ConvertUtils.convertMapToString(retMap));
            ServletUtils.writeToResponse(response, retMap);
        } else {
            logger.info("【/api/user/getTrip.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }

    }

    /**
     * 修改行程状态
     * @param id
     * @param scheduleStatus
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/trip/updateStatus.htm" )
    public void updateStatus (
            @RequestParam( value = "id") Long id,
            @RequestParam( value = "scheduleStatus") Integer scheduleStatus,
            @RequestParam( value = "gbs", required = false) String gbs)
            throws Exception {
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            logger.info("【/api/user/trip/updateStatus.htm】 【inputs】  id = " + id +
                    ", scheduleStatus = " + scheduleStatus);
            TbTrip tbTrip = tbTripService.getLastTrip( tbUser.getId() );
            if ( tbTrip != null && DateUtil.minutesBetween(tbTrip.getCreateDate(), new Date()) >= Global.getInt("trip_minutes")) {
                if (tbTripService.updateStatus(id, scheduleStatus, gbs)) {
                    logger.info("【/api/user/trip/updateStatus.htm】【outputs】 操作成功");
                    ServletUtils.writeToResponse(response, BaseResponse.success());
                } else {
                    logger.info("【/api/user/trip/updateStatus.htm】【outputs】 操作失败");
                    ServletUtils.writeToResponse(response, BaseResponse.fail());
                }
            } else {
                logger.info("【/api/user/trip/updateStatus.htm】【outputs】 操作失败");
                ServletUtils.writeToResponse(response, BaseResponse.fail("行程结束时间距开始时间太短"));
            }
        } else {
            logger.info("【/api/user/trip/updateStatus.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }



    }

    /**
     * 修改行程信息
     * @param id
     * @param estimateDate
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/updateTrip.htm" )
    public void updateTrip (
            @RequestParam( value = "id") Long id,
            @RequestParam( value = "estimateDate") Integer estimateDate,
            @RequestParam( value = "gbs", required = false) String gbs)
            throws Exception {
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            logger.info("【/api/user/updateTrip.htm】 【inputs】 id = " + id + " + estimateDate = " + estimateDate
                   );
            TbTrip tbTrip = tbTripService.getLastTrip( tbUser.getId() );
            if ( tbTrip != null ) {
                Date estimate = DateUtil.dateAddMins(tbTrip.getEstimateDate(), estimateDate);
                if (tbTripService.updateTrip(id, estimate, gbs)) {
                    logger.info("【/api/user/updateTrip.htm】【outputs】 操作成功");
                    ServletUtils.writeToResponse(response, BaseResponse.success());
                } else {
                    logger.info("【/api/user/updateTrip.htm】【outputs】 操作失败");
                    ServletUtils.writeToResponse(response, BaseResponse.fail());
                }
            } else {
                logger.info("【/api/user/updateTrip.htm】【outputs】 行程不存在 userId = " + tbUser.getId());
                ServletUtils.writeToResponse(response, BaseResponse.fail());
            }

        } else {
            logger.info("【/api/user/updateTrip.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }
    }

}