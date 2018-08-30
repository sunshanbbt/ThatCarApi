package com.zhengshun.touch.api.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.zhengshun.touch.api.common.mapper.BaseMapper;
import com.zhengshun.touch.api.common.service.impl.BaseServiceImpl;
import com.zhengshun.touch.api.domain.TbEmerContact;
import com.zhengshun.touch.api.domain.TbUser;
import com.zhengshun.touch.api.mapper.TbEmerContactMapper;
import com.zhengshun.touch.api.mapper.TbUserMapper;
import com.zhengshun.touch.api.service.TbUserEmerContactService;
import com.zhengshun.touch.api.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbUserEmerContactServiceImp extends BaseServiceImpl<TbEmerContact, Long> implements TbUserEmerContactService {

    public static final Logger logger = LoggerFactory.getLogger(TbUserEmerContactServiceImp.class);
    @Autowired
    private TbEmerContactMapper tbEmerContactMapper;
    @Autowired
    private TbUserService tbUserService;


    @Override
    public BaseMapper<TbEmerContact, Long> getMapper() {
        return tbEmerContactMapper;
    }


    @Override
    public Boolean saveEmerContact( String data, String rdSessionKey) {
        TbUser tbUser = tbUserService.getUserByRdSessionKey( rdSessionKey );
        if ( tbUser != null ) {
            com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(data);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = JSONObject.parseObject(jsonArray.getString(i));
                    TbEmerContact tbEmerContact = new TbEmerContact();
                    tbEmerContact.setUserId( tbUser.getId() );
                    tbEmerContact.setPhone(jsonObject.getString("phone"));
                    tbEmerContact.setCreateDate( new Date() );
                    tbEmerContactMapper.save( tbEmerContact );
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<TbEmerContact> getListByUser(String rdSessionKey) {
        TbUser tbUser = tbUserService.getUserByRdSessionKey( rdSessionKey );
        if ( tbUser != null ) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", tbUser.getId());
            List<TbEmerContact> list = tbEmerContactMapper.listSelective( paramMap );
            return list;
        }
        return null;
    }

    @Override
    public Boolean deleteEmerContact(Long id, String rdSessionKey) {
        TbUser tbUser = tbUserService.getUserByRdSessionKey( rdSessionKey );
        if ( tbUser != null ) {
           int res = tbEmerContactMapper.deleteById( id );
           if ( res > 0 ) {
               return true;
           }
        }
        return false;
    }
}