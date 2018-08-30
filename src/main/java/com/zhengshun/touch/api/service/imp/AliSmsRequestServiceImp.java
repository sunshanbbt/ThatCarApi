package com.zhengshun.touch.api.service.imp;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zhengshun.touch.api.common.context.Global;
import com.zhengshun.touch.api.common.util.DateUtil;
import com.zhengshun.touch.api.common.util.NidGenerator;
import com.zhengshun.touch.api.domain.TbSms;
import com.zhengshun.touch.api.mapper.TbSmsMapper;
import com.zhengshun.touch.api.service.AliSmsRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AliSmsRequestServiceImp implements AliSmsRequestService {


    public static final Logger logger = LoggerFactory.getLogger(AliSmsRequestServiceImp.class);

    @Autowired
    private TbSmsMapper tbSmsMapper;

    @Override
    public Boolean sendShortMessage(String phone, String code, String messageTempletCode, String type) {
        logger.info("【AliSmsRequestServiceImp】【sendShortMessage】 begin ...");
        try {
            String domain = Global.getValue("ali_sms_domain");
            String product = Global.getValue("ali_sms_product");
            String accessKey = Global.getValue("ali_sms_access_key_id");
            String accessSecret = Global.getValue("ali_sms_access_key_secret");
            String signName = Global.getValue("ali_sms_sign_name");

            String orderNo = NidGenerator.getOrderNo();
            TbSms tbSms = new TbSms(phone, DateUtil.getNow(), "验证码", code, "10", type, "10");
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(messageTempletCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam("{\"code\":\"" + code + "\"}");

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(orderNo);

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);


            tbSms.setResp(sendSmsResponse.getMessage());
            tbSms.setRespTime(DateUtil.getNow());
            tbSms.setOrderNo(sendSmsResponse.getBizId());
            if (sendSmsResponse.getCode().equals("OK")) {
                tbSms.setState("30");
                tbSmsMapper.save(tbSms);
                logger.info("【AliSmsRequestServiceImp】【sendShortMessage】 send success end");
                return true;
            } else {
                tbSms.setState("20");
                tbSmsMapper.save(tbSms);
                logger.info("【AliSmsRequestServiceImp】【sendShortMessage】 send failed end");
                return false;
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;

    }


    @Override
    public Boolean sendShortMessage(String mobile, String content, String type) {
        return null;
    }
}