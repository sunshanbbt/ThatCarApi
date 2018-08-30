package com.zhengshun.touch.api.service;

import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.TbEmerContact;
import com.zhengshun.touch.api.domain.TbTrip;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface TbUserEmerContactService extends BaseService<TbEmerContact, Long> {

    Boolean saveEmerContact(String data,  String rdSessionKey);

    List<TbEmerContact> getListByUser(String rdSessionKey);

    Boolean deleteEmerContact(Long id, String rdSessionKey);

//    Boolean updateEmerContact(Long id, Integer d, String rdSessionKey);
}