package com.zhengshun.touch.api.controller;

import com.zhengshun.touch.api.common.BaseResponse;
import com.zhengshun.touch.api.common.util.ServletUtils;
import com.zhengshun.touch.api.common.web.controller.BaseController;
import com.zhengshun.touch.api.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class TbUserController extends BaseController {


    public static final Logger logger = LoggerFactory.getLogger(TbUserController.class);

    @Resource
    private TbUserService tbUserService;

    @RequestMapping( value = "/api/user/saveUser.htm" )
    public void saveUser (
            @RequestParam( value = "avatarUrl") String avatarUrl,
            @RequestParam( value = "city") String city,
            @RequestParam( value = "country") String country,
            @RequestParam( value = "gender") Integer gender,
            @RequestParam( value = "language") String language,
            @RequestParam( value = "nickName") String nickName,
            @RequestParam( value = "rdSessionKey") String rdSessionKey,
            @RequestParam( value = "province") String province)
            throws Exception {
        logger.info( "【/api/user/saveUser.htm】【inputs】 avatarUrl = " + avatarUrl + ", city = " + city + ", country = " + country + ", gender = " + gender + ", language = " + language
         + ", nickName = " + nickName + ", rdSessionKey = " + rdSessionKey + ", province = " + province);
        if (tbUserService.saveUser( request, avatarUrl, city, country, gender, language, nickName, rdSessionKey,
                province )){
            logger.info("【/api/user/saveUser.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/saveUser.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }

    @RequestMapping( value = "/api/user/update/info.htm" )
    public void updateInfo (
            @RequestParam( value = "wxNo") String wxNo,
            @RequestParam( value = "realName") String realName,
            @RequestParam( value = "rdSessionKey") String rdSessionKey)
            throws Exception {
        logger.info( "【/api/user/update/saveUserName.htm】【inputs】 wxNo = " + wxNo + ", realName = " + realName + ", " +
                "rdSessionKey = " + rdSessionKey);
        if (tbUserService.updateInfo( wxNo, realName,rdSessionKey)){
            logger.info("【/api/user/update/saveUserName.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/update/saveUserName.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }

    @RequestMapping( value = "/api/user/update/unlockPwd.htm" )
    public void updateUnlockPwd (
            @RequestParam( value = "unlockPwd") String unlockPwd,
            @RequestParam( value = "rdSessionKey") String rdSessionKey)
            throws Exception {
        logger.info( "【/api/user/update/unlockPwd.htm】【inputs】 rdSessionKey = " + rdSessionKey);
        if (tbUserService.updateUnlockPwd( request, unlockPwd, rdSessionKey)){
            logger.info("【/api/user/update/unlockPwd.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/update/unlockPwd.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }


    @RequestMapping( value = "/api/user/verify/pwd.htm" )
    public void verifyUnlockPwd (
            @RequestParam( value = "pwd") String pwd,
            @RequestParam( value = "rdSessionKey") String rdSessionKey)
            throws Exception {
        logger.info( "【/api/user/verify/pwd.htm】【inputs】 rdSessionKey = " + rdSessionKey);
        if (tbUserService.verifyPwd( request, pwd, rdSessionKey)){
            logger.info("【/api/user/verify/pwd.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/verify/pwd.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }
    @RequestMapping( value = "/api/user/update/riskPwd.htm" )
    public void updateRickPwd (
            @RequestParam( value = "riskPwd") String riskPwd,
            @RequestParam( value = "rdSessionKey") String rdSessionKey)
            throws Exception {
        logger.info( "【/api/user/update/riskPwd.htm】【inputs】 rdSessionKey = " + rdSessionKey);
        if (tbUserService.updateRiskPwd( request, riskPwd, rdSessionKey)){
            logger.info("【/api/user/update/riskPwd.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/update/riskPwd.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }



}