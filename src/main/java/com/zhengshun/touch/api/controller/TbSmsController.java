package com.zhengshun.touch.api.controller;

import com.zhengshun.touch.api.common.BaseResponse;
import com.zhengshun.touch.api.common.util.ServletUtils;
import com.zhengshun.touch.api.common.web.controller.BaseController;
import com.zhengshun.touch.api.service.TbSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class TbSmsController extends BaseController {


    public static final Logger logger = LoggerFactory.getLogger(TbSmsController.class);

    @Resource
    private TbSmsService tbSmsService;

//    @RequestMapping( value = "/api/sms/getCode.htm" )
//    public void getCode (
//            @RequestParam( value = "phone") String phone)
//            throws Exception {
//        logger.info( "【/api/sms/getCode.htm】【inputs】 phone = " + phone );
//
//        if (tbSmsService.sendCode(phone, "code")){
//            logger.info("【/api/sms/getCode.htm】【outputs】 操作成功");
//            ServletUtils.writeToResponse(response, BaseResponse.success());
//        } else {
//            logger.info("【/api/sms/getCode.htm】【outputs】 操作失败");
//            ServletUtils.writeToResponse( response, BaseResponse.fail() );
//        }
//
//
//    }


}