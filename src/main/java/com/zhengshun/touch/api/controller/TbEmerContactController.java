package com.zhengshun.touch.api.controller;

import com.zhengshun.touch.api.common.BaseResponse;
import com.zhengshun.touch.api.common.MsgUtils;
import com.zhengshun.touch.api.common.context.Constant;
import com.zhengshun.touch.api.common.util.ServletUtils;
import com.zhengshun.touch.api.common.web.controller.BaseController;
import com.zhengshun.touch.api.domain.TbEmerContact;
import com.zhengshun.touch.api.domain.TbTrip;
import com.zhengshun.touch.api.service.TbTripService;
import com.zhengshun.touch.api.service.TbUserEmerContactService;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TbEmerContactController extends BaseController {


    public static final Logger logger = LoggerFactory.getLogger(TbEmerContactController.class);

    @Resource
    private TbUserEmerContactService tbUserEmerContactService;

    @RequestMapping( value = "/api/user/emer/save.htm" )
    public void saveEmer (
            @RequestParam( value = "data") String data,
                @RequestParam( value = "rdSessionKey") String rdSessionKey)
            throws Exception {
        logger.info( "【/api/user/emer/save.htm】【inputs】 data = " + data + ", rdSessionKey = " + rdSessionKey);
        if (tbUserEmerContactService.saveEmerContact( data, rdSessionKey)){
            logger.info("【/api/user/emer/save.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/emer/save.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }

    @RequestMapping( value = "/api/user/emer/get.htm" )
    public void getTrip (
            @RequestParam( value = "rdSessionKey") String rdSessionKey)
            throws Exception {
        logger.info( "【/api/user/emer/get.htm】【inputs】  " );

        List<TbEmerContact> tbEmerContacts = tbUserEmerContactService.getListByUser( rdSessionKey );

        Map<String, Object> retMap = new HashMap<>();

        retMap.put( Constant.RESPONSE_DATA, tbEmerContacts );
        retMap.put( Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE );
        retMap.put( Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
        logger.info( "【/api/user/emer/get.htm】【outputs】 " + ConvertUtils.convert( retMap ) );
        ServletUtils.writeToResponse( response, retMap );


    }



}