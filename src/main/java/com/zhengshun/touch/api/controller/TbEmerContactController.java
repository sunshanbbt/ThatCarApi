package com.zhengshun.touch.api.controller;

import com.zhengshun.touch.api.common.BaseResponse;
import com.zhengshun.touch.api.common.MsgUtils;
import com.zhengshun.touch.api.common.context.Constant;
import com.zhengshun.touch.api.common.util.ServletUtils;
import com.zhengshun.touch.api.common.web.controller.BaseController;
import com.zhengshun.touch.api.domain.TbEmerContact;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.service.TbUserEmerContactService;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TbEmerContactController extends BaseController {


    public static final Logger logger = LoggerFactory.getLogger(TbEmerContactController.class);

    @Resource
    private TbUserEmerContactService tbUserEmerContactService;

    /**
     * 保存联系人
     * @param data
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/emer/save.htm" )
    public void saveEmer (
            @RequestParam( value = "data") String data)
            throws Exception {
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            logger.info("【/api/user/emer/save.htm】 【inputs】 data = " + data + ", userId = " + tbUser.getId());
            String result = tbUserEmerContactService.saveEmerContact(data, tbUser.getId());
            if ( result.equals("true") ) {
                logger.info("【/api/user/emer/save.htm】【outputs】 操作成功");
                ServletUtils.writeToResponse(response, BaseResponse.success());
            } else if ( result.equals("false")) {
                logger.info("【/api/user/emer/save.htm】【outputs】 操作失败");
                ServletUtils.writeToResponse(response, BaseResponse.fail());
            } else {
                logger.info("【/api/user/emer/save.htm】【outputs】 "+result+"");
                ServletUtils.writeToResponse(response, BaseResponse.fail( result ));
            }

        } else {
            logger.info("【/api/user/emer/save.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }
    }

    /**
     * 获取联系人
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/emer/get.htm" )
    public void getEmer () throws Exception {
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            logger.info("【/api/user/emer/get.htm】 【inputs】 userId = " + tbUser.getId());
            List<TbEmerContact> tbEmerContacts = tbUserEmerContactService.getListByUser(tbUser.getId());
            Map<String, Object> retMap = new HashMap<>();

            retMap.put(Constant.RESPONSE_DATA, tbEmerContacts);
            retMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            retMap.put(Constant.RESPONSE_CODE_MSG, MsgUtils.OPERATE_SUCCESS_MSG);
            logger.info("【/api/user/emer/get.htm】【outputs】 " + com.zhengshun.touch.api.common.util.ConvertUtils.convertMapToString(retMap));
            ServletUtils.writeToResponse(response, retMap);
        } else {
            logger.info("【/api/user/emer/get.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }
    }


    /**
     * 删除联系人
     * @throws Exception
     */
    @RequestMapping( value = "/api/user/emer/delete.htm" )
    public void deleteEmer (
            @RequestParam( value = "id") Long id)
            throws Exception {
        TbUser tbUser = getUser( request );
        if ( tbUser != null ) {
            logger.info("【/api/user/emer/delete.htm】 【inputs】 id = " + id + ", userId = " + tbUser.getId());
            if (tbUserEmerContactService.deleteEmerContact(id)) {
                logger.info("【/api/user/emer/delete.htm】【outputs】 操作成功");
                ServletUtils.writeToResponse(response, BaseResponse.success());
            } else {
                logger.info("【/api/user/emer/delete.htm】【outputs】 操作失败");
                ServletUtils.writeToResponse(response, BaseResponse.fail());
            }
        } else {
            logger.info("【/api/user/emer/delete.htm】【outputs】 未找到用户");
            ServletUtils.writeToResponse(response, BaseResponse.fail());
        }
    }
}