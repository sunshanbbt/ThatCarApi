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

    /**
     * 保存用户微信信息
     * @param avatarUrl
     * @param city
     * @param country
     * @param gender
     * @param language
     * @param nickName
     * @param province
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/saveUser.htm" )
    public void saveUser (
            @RequestParam( value = "avatarUrl") String avatarUrl,
            @RequestParam( value = "city") String city,
            @RequestParam( value = "country") String country,
            @RequestParam( value = "gender") Integer gender,
            @RequestParam( value = "language") String language,
            @RequestParam( value = "nickName") String nickName,
            @RequestParam( value = "province") String province)
            throws Exception {
        logger.info( "【/api/user/saveUser.htm】【inputs】 avatarUrl = " + avatarUrl + ", city = " + city + ", country = " + country + ", gender = " + gender + ", language = " + language
         + ", nickName = " + nickName  + ", province = " + province);
        String rdSessionKey = request.getHeader("rdSessionKey");
        logger.info("【/api/user/saveUser.htm】 【inputs】 rdSessionKey = " + rdSessionKey);
        if (tbUserService.saveUser( request, avatarUrl, city, country, gender, language, nickName, rdSessionKey,
                province )){
            logger.info("【/api/user/saveUser.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/saveUser.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }

    /**
     * 修改用户信息
     * @param wxNo
     * @param realName
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/update/info.htm" )
    public void updateInfo (
            @RequestParam( value = "wxNo") String wxNo,
            @RequestParam( value = "realName") String realName,
            @RequestParam( value = "phone") String phone)
            throws Exception {
        logger.info( "【/api/user/update/saveUserName.htm】【inputs】 wxNo = " + wxNo + ", realName = " + realName + ", " +
                "phone = " + phone);
        String rdSessionKey = request.getHeader("rdSessionKey");
        logger.info("【/api/user/update/saveUserName.htm】 【inputs】 rdSessionKey = " + rdSessionKey);
        if (tbUserService.updateInfo( wxNo, realName,phone,rdSessionKey)){
            logger.info("【/api/user/update/saveUserName.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/update/saveUserName.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }

    /**
     * 修改解锁密码
     * @param unlockPwd
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/update/unlockPwd.htm" )
    public void updateUnlockPwd (
            @RequestParam( value = "unlockPwd") String unlockPwd)
            throws Exception {
        String rdSessionKey = request.getHeader("rdSessionKey");
        logger.info("【/api/user/update/unlockPwd.htm】 【inputs】 rdSessionKey = " + rdSessionKey);
        if (tbUserService.updateUnlockPwd( request, unlockPwd, rdSessionKey)){
            logger.info("【/api/user/update/unlockPwd.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/update/unlockPwd.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }

    /**
     * 校验密码
     * @param pwd
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/verify/pwd.htm" )
    public void verifyUnlockPwd (
            @RequestParam( value = "pwd") String pwd)
            throws Exception {
        String rdSessionKey = request.getHeader("rdSessionKey");
        logger.info("【/api/user/verify/pwd.htm】 【inputs】 rdSessionKey = " + rdSessionKey);
        if (tbUserService.verifyPwd( request, pwd, rdSessionKey)){
            logger.info("【/api/user/verify/pwd.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/verify/pwd.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }

    /**
     * 修改风控密码
     * @param riskPwd
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/update/riskPwd.htm" )
    public void updateRickPwd (
            @RequestParam( value = "riskPwd") String riskPwd)
            throws Exception {
        String rdSessionKey = request.getHeader("rdSessionKey");
        logger.info("【/api/user/update/riskPwd.htm】 【inputs】 rdSessionKey = " + rdSessionKey);
        if (tbUserService.updateRiskPwd( request, riskPwd, rdSessionKey)){
            logger.info("【/api/user/update/riskPwd.htm】【outputs】 操作成功");
            ServletUtils.writeToResponse(response, BaseResponse.success());
        } else {
            logger.info("【/api/user/update/riskPwd.htm】【outputs】 操作失败");
            ServletUtils.writeToResponse( response, BaseResponse.fail() );
        }


    }



}