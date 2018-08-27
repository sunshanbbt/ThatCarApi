package com.zhengshun.touch.api.system.security.freemarker.directive;

import com.zhengshun.touch.api.system.security.userdetails.UserFunction;
import com.zhengshun.touch.api.system.security.userdetails.UserRole;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.security.core.context.SecurityContextHolder;
import tool.util.StringUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Spring Security的URL授权指令, 名称为secAuthorizeUrl, 使用如下所示: <br>
 * <pre>#secAuthorizeUrl("/index.htm")</pre>
 * <pre>...</pre>
 * <pre>#end</pre>
 * 
 * 扩展自freemarker TemplateDirectiveModel类, 用于对判断当前登录用户是否被授权访问传入的URL, 如果<br>
 * 允许访问, 则将指令之间的内容输出, 反之不输出<br>
 * 
 * 注意: 该指令不能用于
 * 
 * 用户未登录的页面, 否则会抛出异常
 * @author zhangyz
 */
public class SpringSecurityAuthorizeUrlDirective implements TemplateDirectiveModel {
	
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] temp,
			TemplateDirectiveBody body) throws TemplateException, IOException{
        if (params.isEmpty()) {
            throw new TemplateModelException(
                    "This directive doesn't allow parameters.");
        }
        
        if (body != null) {
        	boolean b = false;
        	String url =  params.get("value").toString();
            if (StringUtil.isNotBlank(url)) {
                // TODO; 改进此处, 增加对用户是否登录的判断
                UserRole userRole = (UserRole) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
                Map<String, UserFunction> resourceMap = userRole.getFunctionMap();
                if (resourceMap != null && !resourceMap.isEmpty()) {
                    if (resourceMap.containsKey(url)) {
                        b = true;
                    }
                }
            } 
            if (b) {
                body.render(env.getOut());
            }
        } else {
        	throw new NullPointerException("missing body");
        }
	}
}
