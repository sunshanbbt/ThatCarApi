package com.zhengshun.touch.api.service;

import com.zhengshun.touch.api.common.service.BaseService;
import com.zhengshun.touch.api.domain.TbEmerContact;
import com.zhengshun.touch.api.domain.TbTrip;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface TbUserEmerContactService extends BaseService<TbEmerContact, Long> {

    String saveEmerContact(String data,  Long userId);

    List<TbEmerContact> getListByUser(Long userId);

    Boolean deleteEmerContact(Long id);

//    Boolean updateEmerContact(Long id, Integer d, String rdSessionKey);
}