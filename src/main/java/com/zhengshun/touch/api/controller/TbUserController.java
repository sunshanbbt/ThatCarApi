package com.zhengshun.touch.api.controller;

import com.zhengshun.touch.api.common.BaseResponse;
import com.zhengshun.touch.api.common.MsgUtils;
import com.zhengshun.touch.api.common.context.Constant;
import com.zhengshun.touch.api.common.util.ConvertUtils;
import com.zhengshun.touch.api.common.util.ServletUtils;
import com.zhengshun.touch.api.common.web.controller.BaseController;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            if (tbUserService.saveUser(request, avatarUrl, city, country, gender, language, nickName, tbUser.getId(),
                    province)) {
                logger.info("【/api/user/saveUser.htm】【outputs】 操作成功");
                ServletUtils.writeToResponse(response, BaseResponse.success());
            } else {
                logger.info("【/api/user/saveUser.htm】【outputs】 操作失败");
                ServletUtils.writeToResponse(response, BaseResponse.fail());
            }

        } else {
            logger.info("【/api/user/saveUser.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
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
            @RequestParam( value = "wxNo", required = false) String wxNo,
            @RequestParam( value = "realName", required = false) String realName,
            @RequestParam( value = "phone", required = false) String phone)
            throws Exception {
        logger.info( "【/api/user/update/saveUserName.htm】【inputs】 wxNo = " + wxNo + ", realName = " + realName + ", " +
                "phone = " + phone);
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            if (tbUserService.updateInfo(wxNo, realName, phone, tbUser.getId())) {
                logger.info("【/api/user/update/saveUserName.htm】【outputs】 操作成功");
                ServletUtils.writeToResponse(response, BaseResponse.success());
            } else {
                logger.info("【/api/user/update/saveUserName.htm】【outputs】 操作失败");
                ServletUtils.writeToResponse(response, BaseResponse.fail());
            }
        } else {
            logger.info("【/api/user/update/saveUserName.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }


    }


    /**
     * 获取用户信息
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/get.htm" )
    public void get ()throws Exception {
        logger.info("【/api/user/get.htm】 【inputs】");
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            Map<String, Object> params = new HashMap<>();
            params.put("wxNo", tbUser.getWxNo());
            params.put("realName", tbUser.getRealName());
            params.put("phone", tbUser.getPhone());
            Map<String, Object> retMap = new HashMap<>();

            retMap.put(Constant.RESPONSE_DATA, params);
            retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
            logger.info("【/api/user/get.htm】【outputs】 " + ConvertUtils.convertMapToString(retMap));
            ServletUtils.writeToResponse(response, retMap);
        } else {
            logger.info("【/api/user/get.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
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
        logger.info("【/api/user/update/unlockPwd.htm】 【inputs】 unlockPwd = " + unlockPwd);
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            Map<String, Object> retMap = tbUserService.updateUnlockPwd( tbUser, unlockPwd );
            logger.info("【/api/user/update/unlockPwd.htm】【outputs】 " + ConvertUtils.convertMapToString(retMap));
            ServletUtils.writeToResponse(response, retMap);

        } else {
            logger.info("【/api/user/update/unlockPwd.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
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
        logger.info("【/api/user/verify/pwd.htm】 【inputs】 pwd = " + pwd);
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            Map<String, Object> retMap = tbUserService.verifyPwd( tbUser, pwd );
            logger.info("【/api/user/verify/pwd.htm】【outputs】 " + ConvertUtils.convertMapToString(retMap));
            ServletUtils.writeToResponse(response, retMap);
        } else {
            logger.info("【/api/user/verify/pwd.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
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
        logger.info("【/api/user/update/riskPwd.htm】 【inputs】 riskPwd = " + riskPwd);
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            Map<String, Object> retMap = tbUserService.updateRiskPwd( tbUser, riskPwd );
            logger.info("【/api/user/update/riskPwd.htm】【outputs】 " + ConvertUtils.convertMapToString(retMap));
            ServletUtils.writeToResponse(response, retMap);
        } else {
            logger.info("【/api/user/update/riskPwd.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }
    }



}